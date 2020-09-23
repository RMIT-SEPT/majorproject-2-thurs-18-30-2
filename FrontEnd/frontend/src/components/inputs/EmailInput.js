import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';

function EmailInput (props) {
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
                <Form.Control placeholder={props.naming} type="email" value={props.value} onChange={handleChange} />
            </Col>
        </Form.Group>     
    );
    
}

export default EmailInput;

/* const mapStateToProps = state => ({
    user : state.user
});

const mapDispatchToProps = () => {
    return {
    };
};

export default compose(
    connect(mapStateToProps, mapDispatchToProps()),
    withRouter
)(EmailInput); */