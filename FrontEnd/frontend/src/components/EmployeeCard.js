import React from 'react';
import { Card, Button, Row, Col, Image } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../css/Card.css';

import profilePic from '../images/dwight1.jpg';
import avocado from '../images/avocado.jpg';

class EmployeeCard extends React.Component {
    constructor (props) {
        super(props);
        this.state = {

        };

    }

    render () {
        console.log(this.props.employee)
        return (
            <Card className="employee-card">
                
                <Row >
                    <Col >
                        <Image src={profilePic} className="card-profile-pic" roundedCircle fluid />                              
                    
                    </Col>
                    <Col md="8">
                        <Card.Body> 
                            <Card.Title>{this.props.employee.username}</Card.Title>
                            <Card.Subtitle>
                                {this.props.employee.fName} {this.props.employee.lName}
                            </Card.Subtitle>
                            <Card.Text>
                                {this.props.employee.email}
                            </Card.Text>
                        </Card.Body>
                    </Col>
                </Row>
                <Row>
                    <Col md="3"/>
                    <Col className="card-button">
                        <Link to={"/employee/" + this.props.employee.id}>
                            <Button variant="info" style={{ fontSize : '14px'}}>View Details</Button>
                        </Link>
                    </Col>
                    <Col className="card-button">
                        <Link to={"/employee/" + this.props.employee.id}>
                            <Button variant="dark" style={{ fontSize : '14px'}}>Schedule</Button>
                        </Link>
                    </Col>
                </Row>
                <Card.Footer className="text-muted">
                    2 days ago
                </Card.Footer>
            </Card>
            
            // <Card className="employee-card">
            //     <Card.Img src={avocado} alt="Card image" className="card-background-image" />
            //     <Card.ImgOverlay>
            //     <Card.Body>
            //         <Card.Title>{this.props.employee.username}</Card.Title>
            //         <Card.Subtitle>
            //             {this.props.employee.fName} {this.props.employee.lName}
            //         </Card.Subtitle>
            //         <Card.Text>
            //             {this.props.employee.email}
            //         </Card.Text>
                    
            //         <Link to={"/employee/" + this.props.employee.id}>
                        
            //             <Button variant="primary">View Details</Button>
                        
            //         </Link>
                    
                    
            //     </Card.Body>
            //     </Card.ImgOverlay>
            // </Card>
        
            
        );
    }
}

export default EmployeeCard;