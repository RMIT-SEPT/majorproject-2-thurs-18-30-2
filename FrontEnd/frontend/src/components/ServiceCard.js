import React from 'react';
import { Card, Button } from 'react-bootstrap';
import '../css/Card.css';

function ServiceCard (props) {
    
    function moreDeatils() {
        console.log("Link this to more details");
    }

    return (
        
        <Card className="card">
            <Card.Body>
                <Card.Title>{props.item.service}</Card.Title>
                <Card.Subtitle>
                    {props.item.date}
                </Card.Subtitle>
                <Card.Text>
                    {props.item.startTime}
                </Card.Text>
                <div id="button">
                    <Button variant="primary" onClick={moreDeatils}>View Details</Button>
                    
                </div>
                
            </Card.Body>
        </Card>
    
    );
}

export default ServiceCard;