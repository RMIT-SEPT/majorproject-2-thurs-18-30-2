import React from 'react';
import { Redirect } from 'react-router-dom';
import { Row, Col, 
    Form, Button, Card   
} from 'react-bootstrap';
import FormTemplates from '../form-templates/form-templates'

class FormTemplate extends React.Component {

    // components = {
    //     text : TextInput,
    //     email : EmailInput,
    //     password : PasswordInput,
    //     // address : AddressInput
    // }

    constructor (props) {
        super(props);
        this.state = {
            redirect : null
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
        var values = [];
        var canSubmit = false;
        var ref;
        for (ref in this.componentRefs) {
            values.push(this.componentRefs[ref].current.state);
        }

        if(values[2].valid) {
            // Redirect url after form submission
            this.setState({
                    redirect : this.form.redirect
                }
            )
            event.preventDefault();
        }
    }
    
    render () {
        var html;

        if (this.state.redirect) {
            html = (<Redirect to={this.state.redirect} />);
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
                                                    pos={[4,8]} 
                                                />
                                                
                                            );
                                        }.bind(this)
                                    ) 
                                }
                                <Form.Group as={Row}>
                                    <Col sm={{ span: 8, offset: 4 }}>
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

export default FormTemplate;