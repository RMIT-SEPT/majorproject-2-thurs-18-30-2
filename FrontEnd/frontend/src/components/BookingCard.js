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
    async function cancelBooking() { 
        var startTimeStamp = Date.parse(props.item.scheduleDate + 'T' + props.item.scheduleStartTime);
        var startDate = new Date(startTimeStamp);
        var currDate = new Date();
        var modal = {};
        var diffHours = Math.abs(startDate - currDate) / 36e5;
        if(diffHours < 48) {
            modal = {
                title : 'Cancelation Unsuccessfull',
                body : ' Sorry, bookings can not be canceled within 48 hours of the booked date and time.'
            }
        } else if(startDate < currDate) {
            modal = {
                title : 'Cancelation Unsuccessfull',
                body : ' Sorry, past bookings can not be canceled.'
            }
        } else {
            await api.delete('/booking/deleteBooking/' + props.item.bookingId);
            modal = {
                title : "Successfull delete",
                body : "Booking deleted successfully"
            }
            props.deleteHandler(props.index);
        }
        await props.openModal(modal);
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
                    <Card.Title id="title">{props.item.bServiceName}</Card.Title>
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
            <Card.Footer id="footer">
                Booked at {props.item.bookingCreatedAt}
            </Card.Footer>
            
            
        </Card>
    
    );
    
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