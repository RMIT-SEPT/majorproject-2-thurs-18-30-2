import React from 'react';
import { Card, Button, OverlayTrigger, Popover } from 'react-bootstrap';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import '../css/Card.css';
import cafe from '../images/cafe.jpg';

function BookingCard (props) {
    var mainUser = useSelector(state => state.user);
    function cancelBooking() {
        console.log("Link this to cancel");
    }

    function eSubTitle() {

        let eSub = (<Card.Subtitle >
                        To be serviced by {props.item.employeeFName} {props.item.employeeLName}
                        
                    </Card.Subtitle>);


        if(mainUser.userDetails) {
            if(mainUser.userDetails.empType === 'admin') {
                eSub = (<React.Fragment>
                            <Link to={`/employee/${props.item.employeeId}`} style={{ color : 'black' }}>
                                {eSub}
                            </Link>
                        </React.Fragment>);
            } 
        }

        return eSub;
    }

    
    console.log(props)
    
    return (
        
        <Card className="card">
            
            <Card.Img src={cafe} alt="Card image" thumbnail/>

            <Card.Body>
                
                <OverlayTrigger
                    overlay={
                        <Popover content>
                            {props.item.bServiceDescription}
                        </Popover>
                    }
                >
                    <Card.Title >{props.item.bServiceName}</Card.Title>
                </OverlayTrigger>
                
                <OverlayTrigger
                    overlay={
                        <Popover content>
                            Employee email <strong>{props.item.employeeEmail}</strong>
                        </Popover>
                    }
                >
                    {eSubTitle}
                </OverlayTrigger>
                
                <Card.Text>
                    Booked by {props.item.customerFName} {props.item.customerLName}
                    <br />
                    Booked for {props.item.scheduleDate} 
                    <br />
                    Between {props.item.scheduleStartTime} - {props.item.scheduleEndTime}
                </Card.Text>
                
                <Button variant="danger" onClick={cancelBooking} style={{ float : 'right' }}>Cancel Booking</Button>       
            </Card.Body>
            <Card.Footer>
                Booked at {props.item.bookingCreatedAt}
            </Card.Footer>
            
            
        </Card>
    
    );
}
export default BookingCard;