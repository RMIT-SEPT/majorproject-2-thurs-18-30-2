import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { withRouter } from 'react-router-dom';

class TextInput extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : '',
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
        if(this.props.user.userDetails) {
            if(!this.state.changed) {
                this.setState({
                    value : this.props.user.userDetails.email,
                    changed : true
                });
            }
        }
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

const mapStateToProps = state => ({
    user : state.user
});

const mapDispatchToProps = () => {
    return {
    };
};

export default compose(
    connect(mapStateToProps, mapDispatchToProps()),
    withRouter
)(TextInput);