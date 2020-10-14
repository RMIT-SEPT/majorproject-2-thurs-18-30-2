import * as React from 'react';
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
import { Card, Form, Button } from 'react-bootstrap';
import api from '../app/api';


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


class Schedule extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data : [],
      currentDate : '2020-09-27',
      addedAppointment : {},
      appointmentChanges : {},
      editingAppointment : undefined,
      service : '0',
      employeeID : '12',
      startTime : 8,
      endTime : 18
    };

    this.commitChanges = this.commitChanges.bind(this);
    this.changeAddedAppointment = this.changeAddedAppointment.bind(this);
    this.changeAppointmentChanges = this.changeAppointmentChanges.bind(this);
    this.changeEditingAppointment = this.changeEditingAppointment.bind(this);
    this.handleChangeService = this.handleChangeService.bind(this);
    this.handleChangeEmployee = this.handleChangeEmployee.bind(this);
    this.saveSchedule = this.saveSchedule.bind(this);
  }

  saveSchedule() {
    console.log("stuffs");
  }

  async componentDidMount() {
    //getSchedules/employee/{employeeIdAPI}
    console.log("test");
    await api.get('employeeSchedule/getSchedules/employee/' + this.state.employeeID)
              .then((response) => {
                  this.setState({
                      data : response.data
                  });
                  console.log(response.data);
              }).catch((error) => {
                  this.setState({ 
                      valid : false,
                      errorMsg : error.response.data.message
                  });
              });
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
    console.log("test");
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
            <Paper>
                <Scheduler
                data={data}
                height={750}
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
                <Appointments />
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
            <Button id="submitForm" variant="primary" onClick={this.saveSchedule}>Make Bookings</Button>
        </React.Fragment>
    );
  }
}
export default Schedule;