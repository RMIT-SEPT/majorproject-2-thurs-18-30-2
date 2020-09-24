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
            errorMsg : 'Please fill in all fields correctly',
            inputValues : {},
            inputValidity : {}
        };

        // Form template which is passed down by props.router
        var formTemplates = new FormTemplates();
        this.form = formTemplates[props.router.form]();

        var component;
        for (component of this.form.components) {
            this.state.inputValues[component.input] = '';
            this.state.inputValidity[component.input] = false;
        }
        this.inputChangeValidity = this.inputChangeValidity.bind(this);
        this.inputChangeHandler = this.inputChangeHandler.bind(this);
        this.submitForm = this.submitForm.bind(this);
    }

    inputChangeHandler (inputKey, input) {
        var tmp = this.state.inputValues;
        tmp[inputKey] = input;
        this.setState({
            inputValues : tmp
        })
    }

    inputChangeValidity (inputKey, valid) {
        var tmp = this.state.inputValidity;
        tmp[inputKey] = valid;
        this.setState({
            inputValidity : tmp
        })
    }

    async componentDidMount () {
        if(this.form.prefill) {
            var id = this.props.router.computedMatch.params.eId;
            var valuesToFill;
            if(id) {
                try {
                    const response = await api.get('/employee/getEmployeeById/' + id);
                    
                    valuesToFill = {...response.data};
                    this.form.apiCall += '/' + response.data.username;
                    this.form.redirect += '/' + id;

                } catch(error) {
                    console.log(error.response);
                }
            } else {
                valuesToFill = {...this.props.user.userDetails};

                this.form.apiCall += '/' + this.props.user.userDetails.username;
            }
            
            if(valuesToFill) {
                var tmpValues = {...this.state.inputValues};
                var tmpValidity = {...this.state.inputValidity};

                var component;
                for(component of this.form.components) {
                    
                    tmpValues[component.input] = valuesToFill[component.input];
                    tmpValidity[component.input] = true;
                } 
                
                this.setState({
                    inputValues : {...tmpValues},
                    inputValidity : {...tmpValidity}
                });
            }
            
        }
    }

    async submitForm (event) {
        event.preventDefault();
        
        var valid = true;
        var component;
        for(component of this.form.components) {
            valid = valid && this.state.inputValidity[component.input]
        }
        
        this.setState({
            valid : valid,
            errorMsg : 'Please fill in all fields correctly'
        });
        
        if(this.state.valid) {
            await api.post(this.form.apiCall, this.state.inputValues)
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
                                    
                                            return  (
                                                <TagName 
                                                    key={component.input}
                                                    inputKey={component.input}
                                                    naming={component.inputName} 
                                                    pos={[3,9]} 
                                                    value={this.state.inputValues[component.input]}
                                                    onChange={this.inputChangeHandler}
                                                    changeValid={this.inputChangeValidity}
                                                    validity={this.state.inputValidity[component.input]}
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
    modal : state.modal,
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