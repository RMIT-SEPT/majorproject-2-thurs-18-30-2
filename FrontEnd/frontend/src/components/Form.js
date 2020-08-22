import React from 'react';
import TextInput from './inputs/TextInput'
import EmailInput from './inputs/EmailInput'
import PasswordInput from './inputs/PasswordInput'

class Form extends React.Component {
    constructor (props) {
        super(props);
        this.state = {};
        this.componentRefs = {};
        this.components = {
            text : TextInput,
            email : EmailInput,
            password : PasswordInput,
            // address : AddressInput
        }

        var component;
        for (component of props.form.components) {
            this.componentRefs[component.inputName] = React.createRef();
        }

        this.submitForm = this.submitForm.bind(this);
    }

    submitForm (event) {
        var values = [];
        var ref;
        for (ref in this.componentRefs) {
            values.push(this.componentRefs[ref].current.state);
        }
        console.log(values);
        event.preventDefault();
    }
    
    render () {
        return (
            <form onSubmit={this.submitForm}> 

                <ul>
                    {/* This part dynamically adds input fields to form */}
                    {       
                        this.props.form.components.map(
                            function(component) {
                                var TagName = this.components[component.inputType];
                                return  (
                                    <li key={component.inputName}>
                                        <label> 
                                            {component.inputName}
                                            <TagName ref={this.componentRefs[component.inputName]} />
                                        </label>
                                    </li>
                                );
                            }.bind(this)
                        )
                    }
                </ul>
                <input type="submit" value="Submit" />
            </form>
        );
    }

}

export default Form;