import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';

function PasswordInput (props) {

    function handleChange (event) {
        var input = event.target.value;
        props.onChange(props.inputKey, input);
        props.changeValid(props.inputKey, true);
    }
 
    return (
        <Form.Group as={Row}>
            <Form.Label column sm={props.pos[0]}>
                {props.naming}
            </Form.Label>
            <Col sm={props.pos[1]}>
                <Form.Control placeholder={props.naming} type="password" value={props.value} onChange={handleChange} />
            </Col>
        </Form.Group>
    );
    
}

export default PasswordInput;
