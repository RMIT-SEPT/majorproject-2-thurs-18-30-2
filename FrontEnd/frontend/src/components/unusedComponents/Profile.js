import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
// h2 {
//     text-align: center;
// }
// img {
//     border: 1px solid #ddd;
//     border-radius: 4px;
//     padding: 5px;
//     width: 250px;
//     margin-left: 25px;

// }
// #centre{
//     width: 75%;

//     margin: auto;
// }

// #borderTopAndBottom{
//     border: 1px solid black;
//     border: 1px solid black;
//     text-align: center;
//     padding-top: 15px;
//     margin-bottom: 10px;
//     box-shadow: 5px 8px #888888;
// }
import api from '../app/api';
import '../css/Profile.css';
import { Button } from 'react-bootstrap';

function Profile({ router }) {

    var mainUser = useSelector(state => state.user);
    const [user, setUser] = useState();
    const [editUrl, setEditUrl] = useState('/edit');

    useEffect(() => {
        if(router.computedMatch.params.eId) {
            async function getApi() {
                var url = '/employee/getEmployeeById/' + router.computedMatch.params.eId;
                try {
                    const response = await api.get(url)
            
                    setUser({...response.data});
                    setEditUrl('/edit/employee/' + router.computedMatch.params.eId);

                } catch(error) {
                    console.log(error.response);
                }
            }

            getApi();    
        } else {
            setUser({...mainUser.userDetails})
        }
    }, [mainUser.userDetails, router.computedMatch.params.eId]);


    if(user) {
        return (
            <React.Fragment>  
                <div className="jumbotron jumbotron-fluid">
                    <div id="centre">
                        <div className="row">
                            <div className="col-md-4">
                                <h2><b>{user.username}'s Profile Page</b></h2>
                                <img src="https://storage.pixteller.com/designs/designs-images/2016-11-19/02/thumbs/img_page_1_58305b35ebf5e.png"></img>
                                <Link to={editUrl}>
                                    <Button>Edit</Button>
                                </Link>
                            </div>
                            <div id="profile-border" className="col-md-8">
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Username</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{user.username}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>First Name</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{user.fName}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Last Name</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{user.lName}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Email</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{user.email}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom"className="row">
                                    <div className="col-md-6">
                                        <b><label>Phone</label></b>
                                    </div>
                                    <div  className="col-md-6">
                                        <p>{user.pNumber}</p>
                                    </div>
                                </div>
                                <div id="borderTopAndBottom" className="row">
                                    <div className="col-md-6">
                                        <b><label>Address</label></b>
                                    </div>
                                    <div className="col-md-6">
                                        <p>{user.address}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    } else {
        return(
            <React.Fragment>Not Logged In</React.Fragment>
        );
    }
}

export default Profile;
