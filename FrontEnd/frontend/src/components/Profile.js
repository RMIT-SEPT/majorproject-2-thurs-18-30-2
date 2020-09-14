import React from 'react';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { withRouter } from 'react-router-dom';
import { Row, Col, Form } from 'react-bootstrap';

class Profile extends React.Component {
    constructor (props) {
        super(props);
        this.state = {

        };
    }
    //A contact us page
    render () {
        if(this.props.user.userDetails)
        {
            return (
                <Form.Group>
                    <h2><b>Profile Page</b></h2>
                    
                    <Row>
                        <Col>Name</Col>
                        <Col>{this.props.user.userDetails.fName} {this.props.user.userDetails.lName}</Col>
                    </Row>
                    <Row>
                        <Col>Username</Col>
                        <Col>{this.props.user.userDetails.Username}</Col>
                    </Row>
                    <Row>
                        <Col>Password</Col>
                        <Col>{this.props.user.userDetails.Password}</Col>

                    </Row>
                    <Row>
                        <Col>Address</Col>
                        <Col>{this.props.user.userDetails.Address}</Col>

                    </Row>
                    <Row>
                        <Col>Contact Number</Col>
                        <Col>{this.props.user.userDetails.Contact}</Col>

                    </Row>
                </Form.Group>
            );
        }
    
    else
    {
        return( 
            <html>Not Logged In</html>
        );
    }
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
)(Profile);