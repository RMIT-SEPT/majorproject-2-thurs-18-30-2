import React from 'react';
import { Form } from 'react-bootstrap';

class PasswordInput extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : ''
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange (event) {
        this.setState({
            value : event.target.value
        });
    }

    render () {
        return (
            <Form.Control placeholder="Password" type="password" value={this.state.value} onChange={this.handleChange} />
        );
    }
}

export default PasswordInput;
