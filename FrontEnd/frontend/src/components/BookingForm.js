import React, { useState } from 'react';
import Paper from '@material-ui/core/Paper';
import { ViewState, EditingState } from '@devexpress/dx-react-scheduler';
import {
  Scheduler,
  Appointments,
  AppointmentForm,
  AppointmentTooltip,
  WeekView,
  MonthView,
  EditRecurrenceMenu,
  AllDayPanel,
  ConfirmationDialog,
} from '@devexpress/dx-react-scheduler-material-ui';
import { Card, Form, Button } from 'react-bootstrap';
import * as _ from 'lodash';
import { openModal } from '../app/reducers/modalSlice';
import { connect } from 'react-redux';
import api from '../app/api';
import '../css/BookingForm.css';


class BookingForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data : [],
      currentDate : '2020-10-20',
      addedAppointment : {},
      appointmentChanges : {},
      editingAppointment : undefined,
      serviceID : '-1',
      employeeID : '-1',
      startTime : 5,
      endTime : 18,
      bookingBuffer : [],
      style : {backgroundColor: '#FFC107', borderRadius: '8px'},
      services : [],
      employees : [],
      modal : { title : "Booking Successful", body : "Thanks for choosing us, you have successfully made a booking" }
    };
    //Binding functions to class
    this.handleChangeService = this.handleChangeService.bind(this);
    this.handleChangeEmployee = this.handleChangeEmployee.bind(this);
    this.appointmentClickHandler = this.appointmentClickHandler.bind(this);
    this.appointmentClickHandlerThrottled = _.throttle(this.appointmentClickHandler, 1000);
    this.appointmentComponent = this.appointmentComponent.bind(this);
    this.makeBookings = this.makeBookings.bind(this);

    this.state.currentDate = new Date();
  }
  //On component loading set the services that can be selected
  async componentDidMount() {
    await api.get('bService/getAllBServices/haveSchedules')
            .then((response) => {
                this.setState({
                    services : response.data
                });
            }).catch((error) => {
                this.setState({
                    errorMsg : error.response.data.message
                });
            });

  }
  //On clicking button, make a booking using the information stored in the booking buffer
  makeBookings() {
    var tempBuffer = this.state.bookingBuffer;
    tempBuffer.forEach(element => 
      api.post('booking/create', element)
            .then((response) => {
                this.setState({
                  bookingBuffer : []
                })
            }).catch((error) => {
                this.setState({
                    valid : false,
                    errorMsg : error.response.data.message
                });
            })
            );
    //Open modal to show booking success
    this.props.openModal(this.state.modal);
  }
  //Component representing a single appointment cell
  appointmentComponent(props) {
    var [style, setStyle] = useState(this.state.style)
    var [selected, setSelected] = useState(false)
    //Functionality for selecting a time slot for adding to booking buffer(visual side only)
    return <Appointments.Appointment {...props} style={ style } onClick={e => {
      if(selected) {
        setSelected(false)
        setStyle({backgroundColor: '#FFC107', borderColor: '#FFC107', borderRadius: '8px'})
      } else {
        setSelected(true)
        setStyle({backgroundColor: '#FFC107', borderColor: '#FF0000', borderRadius: '8px'})
      }
      this.appointmentClickHandlerThrottled(e)}} onDoubleClick={e => this.appointmentClickHandlerThrottled(e)}/>;
  };
  //On click for an appointment, adding it to booking buffer to make booking when make booking is called
  appointmentClickHandler(appt) {
    if(appt) {
      if(this.state.bookingBuffer.findIndex(i => i.employeeSchedule.id === appt.data.employeeScheduleId) === -1) {
        var tempBuffer = [];
        tempBuffer.push({
            employeeSchedule : { id : appt.data.employeeScheduleId },
            customer : { id : this.props.user.userDetails.id }
        });
        
        this.setState({
          bookingBuffer : tempBuffer
        })
        }
        else {
          var index = this.state.bookingBuffer.findIndex(i => i.employeeSchedule.id === appt.data.employeeScheduleId);

          this.state.bookingBuffer.splice(index, 1);
        }
    }
  }
  //On changing the service make the api call to update the list of available employees for the drop down
  handleChangeService(event) {
    this.setState({
        serviceID : event.target.value
    });
    event.persist();
    //Make sure selection is valid
    if(event.target.value !== -1) {
      api.get('employeeSchedule/getEmployees/bService/' + event.target.value)
              .then((response) => {
                  this.setState({
                      employees : response.data
                  });
              }).catch((error) => {
                  alert("No Employees Available For This Service");
              });
    }
    else {
      this.setState({
        employeeID : -1
      })
    }
    
  }

  //On changing the employee make the api call to get the employee's schedule and update the calendar with the data
  handleChangeEmployee(event) {
    event.persist();
    //Make sure that selection is valid before making api call
    if(event.target.value !== -1) {
      api.get('employeeSchedule/getSchedules/employee/available/' + event.target.value)
          .then((response) => {
              var tempData = [];
              response.data.forEach((element, index) =>
                tempData.push(
                  {
                    title : element.bServiceName,
                    startDate : new Date(element.startDate[0], element.startDate[1]-1, element.startDate[2], element.startDate[3], element.startDate[4]),
                    endDate : new Date(element.endDate[0], element.endDate[1]-1, element.endDate[2], element.endDate[3], element.endDate[4]),
                    id : index,
                    employeeScheduleId : element.employeeScheduleId
                  }
                )
              );
              this.setState({
                data : tempData,
                employeeID : event.target.value
              })
              
          }).catch((error) => {
              this.setState({ 
                  errorMsg : error.response.data.message,
                  data : [],
                  employeeID : event.target.value
              });
          });
    }
  }

  render() {
    
    if(this.props.user.userDetails) {
      return (
          <Card>
              <br></br>
              <Form.Group controlId="ServiceSelection">
                  <Form.Label>Select the service you would like to make a booking for</Form.Label>
                  <Form.Control as="select" onChange={this.handleChangeService}>
                      <option key="-1" value="-1">-services-</option>
                      {
                        this.state.services.map(
                        function(data) {
                          if(data) {
                            return(<option key={data.id} value={data.id}>{data.name}</option>);
                          }
                        }
                      )
                      }
                  </Form.Control>
              </Form.Group>
              <br></br>

              {this.state.serviceID !== '-1' &&
                  <Form.Group controlId="EmployeeSelection">
                      <Form.Label>Select the employee you would like to make a booking for</Form.Label>
                      <Form.Control as="select" onChange={this.handleChangeEmployee}>
                          <option key="-1" value="-1">-employees-</option>
                          {
                            this.state.employees.map(
                              function(data) {
                                if(data) {
                                  return(<option key={data.employeeId} value={data.employeeId}>{data.employeeFName}</option>);
                                }
                              }
                            )
                          }
                      </Form.Control>
                  </Form.Group>   
              }
              <br></br>
              {this.state.employeeID !== '-1' &&
                  <React.Fragment>
                    <Paper>
                        <Scheduler
                          data={this.state.data}
                          height={700}
                        >
                        <ViewState
                            currentDate={this.state.currentDate}
                        />
                        <MonthView />
                        <Appointments 
                          appointmentComponent={this.appointmentComponent}
                        />
                        <AppointmentTooltip />
                        </Scheduler>
                    </Paper>
                    <Button id="submitForm" variant="primary" onClick={this.makeBookings}>Make Bookings</Button>
                  </React.Fragment>
              }
              
          </Card>
      );
      }
      else {
        return(<h1>Not Logged In</h1>)
      }
  }
}
//Redux for user state
const mapStateToProps = state => ({
  modal : state.modal,
  user : state.user
});

const mapDispatchToProps = () => {
  return { 
      openModal
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps()
)(BookingForm);