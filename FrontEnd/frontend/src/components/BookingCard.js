import React from 'react';
import { Card, Button } from 'react-bootstrap';
import '../css/Card.css';
import avocado from '../images/avocado.jpg';

function BookingCard (props) {
    
    function moreDeatils() {
        console.log("Link this to more details");
    }
    console.log(props)
    return (
        
        <Card className="card">
            
            <Card.Img src={avocado} alt="Card image"/>
           
            <Card.ImgOverlay >
            
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
            
            </Card.ImgOverlay>
        </Card>
    
    );
}

export default BookingCard;