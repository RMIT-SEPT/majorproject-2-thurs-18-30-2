import React, { useState, useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import { ViewState, EditingState } from '@devexpress/dx-react-scheduler';
import {
  Scheduler,
  Appointments,
  AppointmentForm,
  AppointmentTooltip,
  WeekView,
  EditRecurrenceMenu,
  AllDayPanel,
  ConfirmationDialog,
} from '@devexpress/dx-react-scheduler-material-ui';
import { appointments } from './appointments';
import { Form, Button } from 'react-bootstrap';
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
      data : appointments,
      currentDate : '2020-09-27',
      addedAppointment : {},
      appointmentChanges : {},
      editingAppointment : undefined,
      service : '0',
      employee : '0',
      startTime : 8,
      endTime : 18,
      bookingBuffer : [],
      style : {backgroundColor: '#FFC107', borderRadius: '8px'}
    };

    this.commitChanges = this.commitChanges.bind(this);
    this.changeAddedAppointment = this.changeAddedAppointment.bind(this);
    this.changeAppointmentChanges = this.changeAppointmentChanges.bind(this);
    this.changeEditingAppointment = this.changeEditingAppointment.bind(this);
    this.handleChangeService = this.handleChangeService.bind(this);
    this.handleChangeEmployee = this.handleChangeEmployee.bind(this);
    this.appointmentClickHandler = this.appointmentClickHandler.bind(this);
    this.appointmentComponent = this.appointmentComponent.bind(this);
    this.makeBooking = this.makeBooking.bind(this);
  }

  // componentDidUpdate() {
  //   this.appointmentComponent = (props) => {
  //     return <Appointments.Appointment {...props} style={ this.state.style } onClick={e => this.appointmentClickHandler(e)} onDoubleClick={e => this.appointmentClickHandler(e)}/>;
  //   }
  // }
  makeBooking() {
    //this.state.bookingBuffer.forEach(element => api call to make booking);
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
      
      this.appointmentClickHandler(e)}} onDoubleClick={e => this.appointmentClickHandler(e)}/>;
  };

  appointmentClickHandler(appt) {
    console.log(appt.data.id);
    if(appt) {
      if(this.state.bookingBuffer.indexOf(appt.data.id) === -1) {
          this.state.bookingBuffer.push(appt.data.id);
        }
        else {
          var index = this.state.bookingBuffer.indexOf(appt.data.id);
          this.state.bookingBuffer.splice(index, 1);
        }
        console.log(this.state.bookingBuffer);
    }
  }

  handleChangeService(event) {
    this.setState({
        service : event.target.value
    });
  }

  handleChangeEmployee(event) {
    this.setState({
        employee : event.target.value
    });
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
                    <option value='0'>-services-</option>
                    <option value='1'>1</option>
                    <option value='2'>2</option>
                    <option value='3'>3</option>
                    <option value='4'>4</option>
                    <option value='5'>5</option>
                </Form.Control>
            </Form.Group>
            <br></br>

            {this.state.service !== '0' &&
                <Form.Group controlId="EmployeeSelection">
                    <Form.Label>Select the employee you would like to make a booking for</Form.Label>
                    {/* Insert api call here and map to see all employees that are available */}
                    <Form.Control as="select" onChange={this.handleChangeEmployee}>
                        <option value='0'>-employees-</option>
                        <option value='1'>1</option>
                        <option value='2'>2</option>
                        <option value='3'>3</option>
                        <option value='4'>4</option>
                        <option value='5'>5</option>
                    </Form.Control>
                </Form.Group>   
            }
            <br></br>
            {this.state.employee !== '0' &&
                <React.Fragment>
                  <Paper>
                      <Scheduler
                      data={data}
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
                      <WeekView
                          startDayHour={this.state.startTime}
                          endDayHour={this.state.endTime}
                      />
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
                  <Button id="submitForm" variant="primary">Primary</Button>
                </React.Fragment>
            }
            
        </React.Fragment>
    );
  }
}
export default BookingForm;