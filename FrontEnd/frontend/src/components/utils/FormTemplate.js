import React from 'react';
import api from '../../app/api';
import { Redirect } from 'react-router-dom';
import { Row, Col,
    Form, Button, Card,
    Alert } from 'react-bootstrap';
import { connect } from 'react-redux';

import { logIn } from '../../app/reducers/userSlice';
import { openModal } from '../../app/reducers/modalSlice';
import FormTemplates from '../../form-templates/form-templates';

class FormTemplate extends React.Component {

    constructor (props) {
        super(props);
        this.state = {
            redirect : null,
            valid : true,
            errorMsg : 'Please fill in all fields correctly'
        };

        // Form template which is passed down by props.router
        var formTemplates = new FormTemplates();
        this.form = formTemplates[props.router.form]();

        // Reference to child input components
        this.componentRefs = {};

        var component;
        for (component of this.form.components) {
            this.componentRefs[component.inputName] = React.createRef();
        }

        this.submitForm = this.submitForm.bind(this);

    }

    async submitForm (event) {
        event.preventDefault();

        var user = {};
        var valid = true;
        await this.form.components.map(
            function(component) {
                user[component.input] = this.componentRefs[component.inputName].current.state.value;
                if(this.componentRefs[component.inputName].current.state.valid != null) {
                    valid = valid && this.componentRefs[component.inputName].current.state.valid;
                }
                return true;
            }.bind(this)
        );
        this.setState({
            valid : valid,
            errorMsg : 'Please fill in all fields correctly'
        });
        
        if(this.state.valid) {
            api.post(this.form.apiCall, user)
            .then((response) => {
                if(this.form.responseHandler) {
                    this.props[this.form.responseHandler](response.data);
                }

                if(this.form.modal) {
                    this.props.openModal(this.form.modal);
                }

                this.setState({
                    redirect : this.form.redirect
                });
            }).catch((error) => {
                this.setState({
                    valid : false,
                    errorMsg : error.response.data.message
                });
            });
        }
    }
    
    render () {
        var html;

        if (this.state.redirect) {
            html = (
                <Redirect to={this.state.redirect} />
            );
        } else {
            html = (
                    <Card>
                        <Card.Header>{this.form.header}</Card.Header>
                        <Card.Body>
                            {!this.state.valid &&
                                <Alert variant='danger' >{this.state.errorMsg}</Alert>
                            }
                            <Form onSubmit={this.submitForm}> 

                                {/* This part dynamically adds input fields to form */}
                                {       
                                    this.form.components.map(
                                        function(component) {
                                            var TagName = component.inputType;
                                            var val = null;
                                            if(this.props.user.userDetails) {
                                                val = this.props.user.userDetails[component.input];
                                            }
                                            else {
                                                val = '';
                                            }
                                            return  (
                                                <TagName 
                                                    key={component.inputName} 
                                                    ref={this.componentRefs[component.inputName]} 
                                                    naming={component.inputName} 
                                                    pos={[3,9]} 
                                                    val={val}
                                                />
                                                
                                            );
                                        }.bind(this)
                                    ) 
                                }
                                <br></br>
                                <Form.Group as={Row}>
                                    <Col sm={{ span: 9, offset: 3 }}>
                                        <Button type="submit">{this.form.submitText}</Button>

                                    </Col>
                                </Form.Group>
                            </Form>
                        </Card.Body>
                    </Card>
            );
        }
        return html;
    }

}

const mapStateToProps = state => ({
    modal :  state.modal,
    user : state.user
});

const mapDispatchToProps = () => {
    return { 
        logIn,
        openModal
    };
};

export default connect(
    mapStateToProps,
    mapDispatchToProps()
)(FormTemplate);