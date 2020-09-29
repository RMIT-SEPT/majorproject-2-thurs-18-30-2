import React from 'react';
import { Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../css/Card.css';
import cafe from '../images/cafe.jpg';

function ServiceCard (props) {
    
    return (
        
        <Card className="card">
            <Card.Img src={cafe} alt="Card image" thumbnail/>
            <Card.Body>
                <Card.Title>{props.item.name}</Card.Title>
               
                <Card.Text>
                    {props.item.description}
                </Card.Text>
                <Link to={"booking url"}>
                    <Button variant="primary">Make Booking</Button>
                </Link>    
            </Card.Body>
        </Card>
    
    );
}

export default ServiceCard;