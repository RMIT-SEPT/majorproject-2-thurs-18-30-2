import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import _ from 'lodash';

class EmailInput extends React.Component {
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

        event.persist();
        if (this.debounceFn) {
            this.debounceFn.cancel()
            this.debounceFn = false
          }
        this.debounceFn = _.debounce(() => {
            let searchString = event.target.value;
            // Here should be API call to check if email already exists.
            
        }, 700, { leading : false, trailing: true });

        this.debounceFn();
    }

    render () {
        return (
            <Form.Group as={Row}>
                    <Form.Label column sm={this.props.pos[0]}>
                        {this.props.naming}
                    </Form.Label>
                    <Col sm={this.props.pos[1]}>
                    <Form.Control placeholder={this.props.naming} type="email" value={this.state.value} onChange={this.handleChange} />
                    </Col>
                </Form.Group>
            
        );
    }
}

export default EmailInput;