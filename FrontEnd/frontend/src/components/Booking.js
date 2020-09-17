import React from 'react';
import { Card, Button } from 'react-bootstrap';
import '../css/Booking.css';

class Booking extends React.Component {
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
                    <Card.Title><p>{this.props.booking.service}</p></Card.Title>
                    <Card.Subtitle>
                        <p>{this.props.booking.date}</p>
                    </Card.Subtitle>
                    <Card.Text>
                        <p>{this.props.booking.startTime}</p>
                    </Card.Text>
                    <div id="button">
                        <Button variant="primary">View Details</Button>
                    </div>
                    
                </Card.Body>
            </Card>
        
            </div>
        );
    }
}

export default Booking;