import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';

import Paper from '@material-ui/core/Paper';
import { ViewState } from '@devexpress/dx-react-scheduler';
import { Scheduler, DayView, Appointments } from '@devexpress/dx-react-scheduler-material-ui';

import api from '../app/api';
import profilePic from '../images/dwight1.jpg';
import background from '../images/background3.jpg';
import '../css/Profile.css';
import { Image, Card, Col, Row, Button } from 'react-bootstrap';

function Profile({ router }) {

    var mainUser = useSelector(state => state.user);
    const [user, setUser] = useState();
    const [editUrl, setEditUrl] = useState('/edit');
    
    const currentDate = '2018-11-01';
    const schedulerData = [
  { startDate: '2018-11-01T09:45', endDate: '2018-11-01T11:00', title: 'Meeting' },
  { startDate: '2018-11-01T12:00', endDate: '2018-11-01T13:30', title: 'Go to a gym' }
];

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

    var html;
    if(user) {
        html = (
            <React.Fragment>
            <Card className="bg-dark text-white" style={{marginTop : '20px'}}>
                <Card.Img src={background} alt="Card image" className="bg-dark background-image" />
                <Card.ImgOverlay>
                <Row style={{marginTop : '20px', marginBottom : '20px'}}>
                    <Col md="3">
                        <Image src={profilePic} className="profile-pic" roundedCircle fluid />
                    
                    </Col>
                    <Col md="9">
                        <Card.Body>
                            <Row>
                                <Col xs="10">
                                    <Card.Title style={{fontSize: '30px'}}>{user.username}</Card.Title>
                                </Col>
                                <Col>
                                    <Link to={editUrl}>
                                        <Button variant="info">Edit</Button>
                                    </Link>
                                </Col>
                            </Row>
                            <br />  
                            <Row>
                                <Col md="1" />
                                <Col md="11" style={{ fontSize : '20px' }}>
                                    <Row className="row-spacing">
                                        <Col md="3">
                                            First Name
                                        </Col>
                                        <Col md="9">
                                            {user.fName}
                                        </Col>
                                    </Row>
                                    <Row className="row-spacing">
                                        <Col md="3">
                                            Last Name
                                        </Col>
                                        <Col md="9">
                                            {user.lName}
                                        </Col>
                                    </Row>
                                    <Row className="row-spacing">
                                        <Col md="3">
                                            E-mail
                                        </Col>
                                        <Col md="9">
                                            {user.email}
                                        </Col>
                                    </Row>
                                    <Row className="row-spacing">
                                        <Col md="3">
                                            Username
                                        </Col>
                                        <Col md="9">
                                            {user.username}
                                        </Col>
                                    </Row>
                                    <Row className="row-spacing">
                                        <Col md="3">
                                            Address
                                        </Col>
                                        <Col md="9">
                                            {user.address}
                                        </Col>
                                    </Row>
                                    <Row className="row-spacing">
                                        <Col md="3">
                                            Phone Number
                                        </Col>
                                        <Col md="9">
                                            {user.pNumber}
                                        </Col>
                                    </Row>
                                </Col>
                            </Row>
                            
                            
                        </Card.Body>
                    </Col>
                </Row>
                </Card.ImgOverlay>
            </Card>
            <br></br>
            <Paper>
                <Scheduler
                data={schedulerData}
                >
                <ViewState
                    currentDate={currentDate}
                />
                <DayView
                    startDayHour={9}
                    endDayHour={14}
                />
                <Appointments />
                </Scheduler>
            </Paper>
            </React.Fragment>
        )
    } else {
        html = <React.Fragment>Loading...</React.Fragment>
    }

    return html;
}

export default Profile;
