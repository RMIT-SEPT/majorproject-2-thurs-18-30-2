import React, {useState} from 'react';
import api from '../../app/api';
import { Redirect } from 'react-router-dom';
import { Row, Col,
    Form, Button, Card, Modal} from 'react-bootstrap';
import { connect } from 'react-redux';

import { logIn } from '../../app/reducers/userSlice';
import FormTemplates from '../../form-templates/form-templates';

class FormTemplate extends React.Component {

    constructor (props) {
        super(props);
        this.state = {
            redirect : null,
            showModal : false
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

    submitForm (event) {
        event.preventDefault();

        var user = {};
        this.form.components.map(component => user[component.input] = this.componentRefs[component.inputName].current.state.value);

        api.post(this.form.apiCall, user)
        .then((response) => {
            
            if(this.form.responseHandler) {
                this.props[this.form.responseHandler](response.data);
            }

            this.setState({
                    redirect : this.form.redirect
                }
            )
        })
        .catch((error) => {
            console.log(error);
        });
    }

    /*
    /* Modal function is called when account is registered successfully
    */

    // ModalForm() {
    //     return (
    //         <>
    //             <Modal show={this.state.showModal} onHide={this.state.showModal}>
    //                 <Modal.Body>You have successfully registered!</Modal.Body>
    //                 <Modal.Footer>
    //                     <Button variant="primary" onClick={this.setState({showModal: false})}>
    //                         Close
    //                     </Button>
    //                 </Modal.Footer>
    //             </Modal>
    //              //this is for showing (put in the render function once registration api calls works)
    //              {this.state.showModal && this.ModalForm()}
    //              //this is the button implemented for the modal
    //              <Button onClick={() => this.setState({showModal: true})}type="submit">{this.form.submitText}</Button>
    //         </>
    //     );
    // }
    
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
                            <Form onSubmit={this.submitForm}> 

                                {/* This part dynamically adds input fields to form */}
                                {       
                                    this.form.components.map(
                                        function(component) {
                                            var TagName = component.inputType;
                                            return  (
                                                <TagName 
                                                    key={component.inputName} 
                                                    ref={this.componentRefs[component.inputName]} 
                                                    naming={component.inputName} 
                                                    pos={[3,9]} 
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
});

const mapDispatchToProps = () => {
    return {
        logIn
    };
};

export default connect(
    mapStateToProps,
    mapDispatchToProps()
)(FormTemplate);