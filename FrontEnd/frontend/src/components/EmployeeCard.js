import React from 'react';
import { Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../css/Card.css';

class EmployeeCard extends React.Component {
    constructor (props) {
        super(props);
        this.state = {

        };

    }

    render () {
        return (
            <div id="card">
            <Card id="test">
                <Card.Body>
                    <Card.Title>{this.props.employee.username}</Card.Title>
                    <Card.Subtitle>
                        {this.props.employee.fName} {this.props.employee.lName}
                    </Card.Subtitle>
                    <Card.Text>
                        {this.props.employee.email}
                    </Card.Text>
                    
                    <Link to={"/employee/" + this.props.employee.id}>
                        
                            <Button variant="primary">View Details</Button>
                        
                    </Link>
                    
                    
                </Card.Body>
            </Card>
        
            </div>
        );
    }
}

export default EmployeeCard;