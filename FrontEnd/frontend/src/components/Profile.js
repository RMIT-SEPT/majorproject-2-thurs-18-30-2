import React from 'react';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { withRouter } from 'react-router-dom';
import '../css/Profile.css';

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
                <html>
                <div className="jumbotron jumbotron-fluid">
                    <div id="centre">
                        <div className="row">
                            <div className="col-md-4">
                                <h2><b>{this.props.user.userDetails.username}'s Profile Page</b></h2>
                                <img src="https://storage.pixteller.com/designs/designs-images/2016-11-19/02/thumbs/img_page_1_58305b35ebf5e.png"></img>

                            </div>
                            <div id="profile-border" className="col-md-8">
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Username</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{this.props.user.userDetails.username}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Name</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{this.props.user.userDetails.fName}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Name</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{this.props.user.userDetails.lName}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Email</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{this.props.user.userDetails.email}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom"className="row">
                                    <div className="col-md-6">
                                        <b><label>Phone</label></b>
                                    </div>
                                    <div  className="col-md-6">
                                        <p>{this.props.user.userDetails.pNumber}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Address</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{this.props.user.userDetails.address}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </html>
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