import React from 'react';
import { Form } from 'react-bootstrap';

class TextInput extends React.Component {
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
            <Form.Control type="text" value={this.state.value} onChange={this.handleChange} />
        );
    }
}

export default TextInput;
