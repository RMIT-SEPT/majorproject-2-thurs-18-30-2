import React from 'react';
import { connect } from 'react-redux';
import { compose } from 'redux';

class Profile extends React.Component {
    constructor (props) {
        super(props);
        this.state = {

        };
    }
    //A contact us page
    render () {

        return (
            <html>
                <h2><b>Profile Page</b></h2>
                <h4>Name</h4>
                <h4>Username</h4>
                <h4>Password</h4>
                <h4>Address</h4>
                <h4>Contact Number</h4>
            </html>
        );
    }

}

export default Profile;