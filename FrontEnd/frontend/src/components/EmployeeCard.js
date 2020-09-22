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

    // employeeProfile() {
    //     console.log("link to employee profile");

        
    //     var url = "/employees/" + this.props.id;

    //     return <Redirect path={url} />;
    // }

    render () {
        return (
            <div id="card">
            <Card id="test">
                <Card.Body>
                    <Card.Title><p>{this.props.employee.username}</p></Card.Title>
                    <Card.Subtitle>
                        <p>{this.props.employee.fName} {this.props.employee.lName}</p>
                    </Card.Subtitle>
                    <Card.Text>
                        <p>{this.props.employee.email}</p>
                    </Card.Text>
                    
                    <Link to={"/employee/" + this.props.employee.id}>
                        <div id="button">
                            <Button variant="primary">View Details</Button>
                        </div>
                    </Link>
                    
                    
                </Card.Body>
            </Card>
        
            </div>
        );
    }
}

export default EmployeeCard;