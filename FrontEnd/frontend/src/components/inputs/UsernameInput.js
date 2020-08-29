import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';

class UsernameInput extends React.Component {
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

            
            <Form.Group as={Row}>
                <Form.Label column sm={this.props.pos[0]}>
                    {this.props.naming}
                </Form.Label>
                <Col sm={this.props.pos[1]}>
                    <Form.Control placeholder="Username" type="Username" value={this.state.value} onChange={this.handleChange} />
                </Col>
            </Form.Group>
        );
    }
}

export default UsernameInput;
