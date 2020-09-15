import React from 'react';
import { Card, Button } from 'react-bootstrap';
import '../css/Profile.css';

class Booking extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
        };

    }

    render () {
        return (
            <div class="col-sm">
            <Card style={{ width: '18rem' }}>
                <Card.Body>
                    <Card.Title>{this.props.booking.service}</Card.Title>
                    <Card.Subtitle>
                    {this.props.booking.date}
                    </Card.Subtitle>
                    <Card.Text>
                    {this.props.booking.startTime}
                    </Card.Text>
                    
                <Button variant="primary">View Details</Button>
                </Card.Body>
            </Card>
            </div>
        );
    }
}

export default Booking;