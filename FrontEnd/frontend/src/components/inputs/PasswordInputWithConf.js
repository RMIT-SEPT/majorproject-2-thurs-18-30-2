import React, { useState } from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import _ from 'lodash';

function PasswordInputWithConf (props) {
    var debounceFn;
    var debounceFn1;
    var [valid, setValid] = useState(false);
    var [valid1, setValid1] = useState(false);
    var [border, setBorder] = useState('1px solid lightgrey');
    var [border1, setBorder1] = useState('1px solid lightgrey');
    var [errMsg, setErrMsg] = useState('');
    var [errMsg1, setErrMsg1] = useState('');
    var [valueConf, setValueConf] = useState(props.value);


    function handleChange (event) {
        var input = event.target.value;
        props.onChange(props.inputKey, input);
        event.persist();

        if (debounceFn) {
            debounceFn.cancel()
            debounceFn = false
        }

        debounceFn = _.debounce(() => {
            
            if(event.target.value.length < 8) {
                setValid(false);
                setBorder('1px solid red');
                setErrMsg('Password is too short, please try a longer password');
                props.changeValid(props.inputKey, false);
            } else {  
                setValid(true);
                setBorder('1px solid lightgrey');
                setErrMsg('');
                props.changeValid(props.inputKey, true && valid1);
            }
            
        }, 700, { leading : false, trailing: true });
        
        debounceFn();
    }

    function handleChangeConf (event) {
       
        setValueConf(event.target.value)
        event.persist();
        
        if (debounceFn1) {
            debounceFn1.cancel()
            debounceFn1 = false
        }

        debounceFn1 = _.debounce(() => {
            
            if(props.value === event.target.value) {
                setValid1(true);
                setBorder1('1px solid lightgrey');
                setErrMsg1('');
                props.changeValid(props.inputKey, valid && true);
            }
            else {
                setValid1(false);
                setBorder1('1px solid red');
                setErrMsg1('Passwords do not match, please check and try again');
                props.changeValid(props.inputKey, false);
            }
        }, 700, { leading : false, trailing: true });

        debounceFn1();
    }

    
    return (
        <React.Fragment>
            <Form.Group as={Row}>
                <Form.Label column sm={props.pos[0]}>
                    {props.naming}
                </Form.Label>
                <Col sm={props.pos[1]}>
                    <Form.Control style={{border: border}} placeholder="Password" type="password" value={props.value} onChange={handleChange} />
                    <p style={{color: 'red', fontSize: '12px'}}>
                        {errMsg}
                    </p>
                </Col>
            </Form.Group>
                
            <Form.Group as={Row}>
                <Form.Label column sm={props.pos[0]}>
                    Confirm {props.naming}
                </Form.Label>
                <Col sm={props.pos[1]}>
                    <Form.Control style={{border: border1}} placeholder="Confirm Password" type="password" value={valueConf} onChange={handleChangeConf} />
                    <p style={{color: 'red', fontSize: '12px'}}>
                        {errMsg1}
                    </p>
                </Col>
            </Form.Group>
        </React.Fragment>
    );
    
}

export default PasswordInputWithConf;

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
)(PasswordInputWithConf); */