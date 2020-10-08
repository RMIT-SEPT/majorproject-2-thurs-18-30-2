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
import { Form, Button } from 'react-bootstrap';
import * as _ from 'lodash';
import api from '../app/api';
import '../css/BookingForm.css';




const messages = {
    moreInformationLabel: '',
  };


const TextEditor = (props) => {
// eslint-disable-next-line react/destructuring-assignment
if (props.type === 'multilineTextEditor') {
    return null;
} return <AppointmentForm.TextEditor {...props} />;
};

const ong = (props) => {
    if (props.type === 'multilineTextEditor') {
      return null;
    } return <AppointmentForm.Select {...props} />;
  };
  
  const BasicLayout = ({ onFieldChange, appointmentData, ...restProps }) => {
    const onCustomFieldChange = (nextValue) => {
      onFieldChange({ customField: nextValue });
    };
  
    return (
      <AppointmentForm.BasicLayout
        appointmentData={appointmentData}
        onFieldChange={onFieldChange}
        {...restProps}
      >
        <AppointmentForm.Label
          text="Service"
          type="title"
        />
        <AppointmentForm.Select
          value={appointmentData.customField}
          availableOptions={[
            {
                id : 0,
                text : "Full Body Massage"
            },
            {
                id : 1,
                text : "Super Happy Fun Time"
            },
            {
                id : 2,
                text : "Designer Designing Designs"
            }
        ]}
          onValueChange={onCustomFieldChange}
          placeholder="Custom field"
        />
      </AppointmentForm.BasicLayout>
    );
  };

  



class BookingForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data : [],
      userID : -1,
      currentDate : '2020-09-20',
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
      employees : []
    };

    this.commitChanges = this.commitChanges.bind(this);
    this.changeAddedAppointment = this.changeAddedAppointment.bind(this);
    this.changeAppointmentChanges = this.changeAppointmentChanges.bind(this);
    this.changeEditingAppointment = this.changeEditingAppointment.bind(this);
    this.handleChangeService = this.handleChangeService.bind(this);
    this.handleChangeEmployee = this.handleChangeEmployee.bind(this);
    this.appointmentClickHandler = this.appointmentClickHandler.bind(this);
    this.appointmentClickHandlerThrottled = _.throttle(this.appointmentClickHandler, 1000);
    this.appointmentComponent = this.appointmentComponent.bind(this);
    this.makeBookings = this.makeBookings.bind(this);


  }

  async componentDidMount() {
    this.setState({
        userID : 0
    });
    await api.get('bService/getAllBServices')
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
  async makeBookings() {
    this.state.bookingBuffer.forEach(async element => 
      await api.post('booking/create', element)
            .then((response) => {
                this.setState({
                    services : response.data
                });
            }).catch((error) => {
                this.setState({
                    valid : false,
                    errorMsg : error.response.data.message
                });
            })
            );
    
  }

  appointmentComponent(props) {
    var [style, setStyle] = useState(this.state.style)
    var [selected, setSelected] = useState(false)
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

  appointmentClickHandler(appt) {
    if(appt) {
      if(this.state.bookingBuffer.indexOf(appt.data.id) === -1) {
          this.state.bookingBuffer.push(
            {
              employeeSchedule : { id : appt.data.id },
              customer : { id : this.state.userID }
            });
        }
        else {
          var index = this.state.bookingBuffer.indexOf(appt.data.id);
          this.state.bookingBuffer.splice(index, 1);
        }
    }
  }

  handleChangeService(event) {
    this.setState({
        serviceID : event.target.value
    });
    if(event.target.value !== -1) {
      api.get('employeeSchedule/getBServices/employee/service/' + event.target.value)
              .then((response) => {
                  this.setState({
                      employees : response.data
                  });
              }).catch((error) => {
                  this.setState({ 
                      valid : false,
                      errorMsg : error.response.data.message
                  });
              });
    }
    
  }

  handleChangeEmployee(event) {
    this.setState({
        employeeID : event.target.value
    });
    api.get('employeeSchedule/getSchedules/employee/available/' + event.target.value)
        .then((response) => {
            var count = 0;
            response.data.forEach(element =>
              this.state.data.push(
                {
                  title : element.bServiceName,
                  startDate : new Date(element.startDate[0], element.startDate[1], element.startDate[2], element.startDate[3], element.startDate[4]),
                  endDate : new Date(element.endDate[0], element.endDate[1], element.endDate[2], element.endDate[3], element.endDate[4]),
                  id : count,
                  employeeScheduleId : element.employeeScheduleId
                })
            );
        }).catch((error) => {
            this.setState({ 
                valid : false,
                errorMsg : error.response.data.message
            });
        });
        this.setState({ state: this.state });
  }

  changeAddedAppointment(addedAppointment) {
    this.setState({ addedAppointment });
  }

  changeAppointmentChanges(appointmentChanges) {
    this.setState({ appointmentChanges });
  }

  changeEditingAppointment(editingAppointment) {
    this.setState({ editingAppointment });
  }

  commitChanges({ added, changed, deleted }) {
    this.setState((state) => {
      let { data } = state;
      if (added) {
        const startingAddedId = data.length > 0 ? data[data.length - 1].id + 1 : 0;
        data = [...data, { id: startingAddedId, ...added }];
      }
      if (changed) {
        data = data.map(appointment => (
          changed[appointment.id] ? { ...appointment, ...changed[appointment.id] } : appointment));
      }
      if (deleted !== undefined) {
        data = data.filter(appointment => appointment.id !== deleted);
      }
      return { data };
    });
  }

  render() {
    const {
      currentDate, data, addedAppointment, appointmentChanges, editingAppointment,
    } = this.state;

    return (
        <React.Fragment>
            <br></br>
            <Form.Group controlId="ServiceSelection">
                <Form.Label>Select the service you would like to make a booking for</Form.Label>
                {/* Insert api call here and map to see all services that are available (maybe hardcode) | <br> are for testing purposes only*/}
                
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
                    {/* Insert api call here and map to see all employees that are available */}
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
                          currentDate={currentDate}
                      />
                      <EditingState
                          onCommitChanges={this.commitChanges}

                          addedAppointment={addedAppointment}
                          onAddedAppointmentChange={this.changeAddedAppointment}

                          appointmentChanges={appointmentChanges}
                          onAppointmentChangesChange={this.changeAppointmentChanges}

                          editingAppointment={editingAppointment}
                          onEditingAppointmentChange={this.changeEditingAppointment}
                      />
                      <WeekView />
                      <AllDayPanel />
                      <EditRecurrenceMenu />
                      <ConfirmationDialog />
                      <Appointments 
                        appointmentComponent={this.appointmentComponent}
                      />
                      <AppointmentTooltip
                          showOpenButton
                          showDeleteButton
                      />
                      <AppointmentForm 
                          basicLayoutComponent={BasicLayout}
                          textEditorComponent={TextEditor}
                          selectComponent={ong}
                          messages={messages}
                      />
                      </Scheduler>
                  </Paper>
                  <Button id="submitForm" variant="primary" onClick={this.makeBookings}>Make Bookings</Button>
                </React.Fragment>
            }
            
        </React.Fragment>
    );
  }
}
export default BookingForm;