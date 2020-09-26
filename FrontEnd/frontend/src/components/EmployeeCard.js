import React from 'react';
import { Card, Button, Row, Col, Image } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../css/Card.css';

import profilePic from '../images/dwight1.jpg';
// import avocado from '../images/avocado.jpg';

function EmployeeCard (props) {
        
    return (
        <Card className="card employee-card">
            
            <Row >
                <Col >
                    <Image src={profilePic} className="card-profile-pic" roundedCircle fluid />                              
                
                </Col>
                <Col md="8">
                    <Card.Body> 
                        <Card.Title>{props.item.username}</Card.Title>
                        <Card.Subtitle>
                            {props.item.fName} {props.item.lName}
                        </Card.Subtitle>
                        <Card.Text>
                            {props.item.email}
                        </Card.Text>
                    </Card.Body>
                </Col>
            </Row>
            <Row>
                <Col md="3"/>
                <Col className="card-button">
                    <Link to={"/employee/" + props.item.id}>
                        <Button variant="info" style={{ fontSize : '14px'}}>View Details</Button>
                    </Link>
                </Col>
                <Col className="card-button">
                    <Link to={"/employee/" + props.item.id}>
                        <Button variant="dark" style={{ fontSize : '14px'}}>Schedule</Button>
                    </Link>
                </Col>
            </Row>
            <Card.Footer className="text-muted">
                Created At : {props.item.createdAt}
            </Card.Footer>
        </Card>            
    );
}

export default EmployeeCard;