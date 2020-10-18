import React, { useState } from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import _ from 'lodash';
import api from '../../app/api';

function UsernameInput (props) {
    
    var debounceFn;
    var [border, setBorder] = useState('1px solid lightgrey');
    var [errMsg, setErrMsg] = useState('');

    function handleChange (event) {
        var input = event.target.value;
        props.onChange(props.inputKey, input);

        event.persist();

        if (debounceFn) {
            debounceFn.cancel()
            debounceFn = false
        }

        debounceFn = _.debounce(() => {
            
            api.get(`/user/usernameExists/${event.target.value}`)
            .then((response) => {
                var exists = response.data;
                if(!exists) {
                    setBorder('1px solid lightgrey');
                    setErrMsg('');
                    props.changeValid(props.inputKey, true);
                } else {
                    setBorder('1px solid red');
                    setErrMsg('Username already exists, please try a different username');
                    props.changeValid(props.inputKey, false);
                }
            })
            .catch((error) => {
                console.log(error);
            });
        }, 700, { leading : false, trailing: true });

        debounceFn();
    }
   
    return (
        <Form.Group as={Row}>
            <Form.Label column sm={props.pos[0]}>
                {props.naming}
            </Form.Label>
            <Col sm={props.pos[1]}>
                <Form.Control style={{border: border}} placeholder="Username" type="Username" value={props.value} onChange={handleChange} />
                <p style={{color: 'red', fontSize: '12px'}}>
                    {errMsg}
                </p>
            </Col>
        </Form.Group>
    );
}

export default UsernameInput;


