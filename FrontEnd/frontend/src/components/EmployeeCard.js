import React from 'react';
import { Card, Button } from 'react-bootstrap';
import { Redirect } from 'react-router-dom';
import '../css/Card.css';

class EmployeeCard extends React.Component {
    constructor (props) {
        super(props);
        this.state = {

        };

    }

    employeeProfile() {
        console.log("link to employee profile");
        return <Redirect to='/contact-us'/>;
    }

    render () {
        return (
            <div id="card">
            <Card id="test">
                <Card.Body>
                    <Card.Title><p>{this.props.username}</p></Card.Title>
                    <Card.Subtitle>
                        <p>{this.props.fName} {this.props.lName}</p>
                    </Card.Subtitle>
                    <Card.Text>
                        <p>{this.props.email}</p>
                    </Card.Text>
                    <div id="button">
                        <Button variant="primary" onClick={this.employeeProfile}>View Details</Button>
                    </div>
                    
                </Card.Body>
            </Card>
        
            </div>
        );
    }
}

export default EmployeeCard;