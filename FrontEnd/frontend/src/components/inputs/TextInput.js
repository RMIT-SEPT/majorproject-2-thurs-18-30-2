import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';

class TextInput extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : props.val,
            changed : false
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
            <Form.Group as={Row}>
                <Form.Label column sm={this.props.pos[0]}>
                    {this.props.naming}
                </Form.Label>
                <Col sm={this.props.pos[1]}>
                    <Form.Control type="text" placeholder={this.props.naming} value={this.state.value} onChange={this.handleChange} />
                </Col>
            </Form.Group>
        );
    }
}

export default TextInput;
