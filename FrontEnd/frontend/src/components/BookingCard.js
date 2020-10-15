import React, { useState } from 'react';
import { Card, Button, OverlayTrigger, Popover } from 'react-bootstrap';
import { useSelector } from 'react-redux';
import { Link, Redirect } from 'react-router-dom';
import api from '../app/api';
import '../css/Card.css';
import cafe from '../images/cafe.jpg';
import { connect } from 'react-redux';
import { openModal } from '../app/reducers/modalSlice';

function BookingCard (props) {
    var mainUser = useSelector(state => state.user);
    var [refresh, setRefresh] = useState(false);
    async function cancelBooking() {
        await api.delete('/booking/deleteBooking/' + props.item.bookingId);
        var modal = {
            title : "Successfull delete",
            body : "Booking deleted successfully"
        }
        props.openModal(modal);
        setRefresh(true);
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
    if(refresh) {
        return <Redirect to="/bookings/present"></Redirect>
    } else {
        return (
            
            <Card className="card">
                
                <Card.Img src={cafe} alt="Card image"/>

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
}

const mapStateToProps = state => ({
});

const mapDispatchToProps = () => {
    return { 
        openModal
    };
};

export default connect(
    mapStateToProps,
    mapDispatchToProps()
)(BookingCard);