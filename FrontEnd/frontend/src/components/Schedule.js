import * as React from 'react';
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
      currentDate : '2020-10-10',
      addedAppointment : {},
      appointmentChanges : {},
      editingAppointment : undefined,
      service : 0,
      employeeID : this.props.router.computedMatch.params.eId,
      startTime : 8,
      endTime : 18,
      baseLayout : null
    };

    this.commitChanges = this.commitChanges.bind(this);
    this.changeAddedAppointment = this.changeAddedAppointment.bind(this);
    this.changeAppointmentChanges = this.changeAppointmentChanges.bind(this);
    this.changeEditingAppointment = this.changeEditingAppointment.bind(this);
    this.saveSchedule = this.saveSchedule.bind(this);
    console.log(props);

    this.state.currentDate = new Date();
    this.state.baseLayout = ({ onFieldChange, appointmentData, ...restProps }) => {
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
  }

  saveSchedule() {
    var tempBuffer = this.state.data;
    var convBuffer = [];
    tempBuffer.forEach(element => 
      convBuffer.push( {
        employeeId : parseInt(this.state.employeeID, 10), 
        bServiceId : 3, 
        date : element.startDate.getFullYear() + '-' + (element.startDate.getMonth()+1) + '-' +  element.startDate.getDate(), 
        startTime : element.startDate.toTimeString().split(' ')[0], 
        endTime : element.endDate.toTimeString().split(' ')[0], 
        availability : true,
        employeeScheduleId : element.employeeScheduleId
      })
      //console.log(element)
    );
    convBuffer.forEach(element => 
      api.post('employeeSchedule/editSchedule/' + element.employeeScheduleId, element)
            .then((response) => {
                console.log(response.data);
            }).catch((error) => {
                this.setState({
                    errorMsg : error.response.data.message
                });
            })
            //console.log(element)
    );
  }

  async componentDidMount() {
    
    await api.get('employeeSchedule/getSchedules/employee/' + this.state.employeeID)
              .then((response) => {
                var tempData = [];
                response.data.forEach((element, index) =>
                  tempData.push(
                    {
                      title : element.bServiceName,
                      startDate : new Date(element.startDate[0], element.startDate[1]-1, element.startDate[2], element.startDate[3], element.startDate[4]),
                      endDate : new Date(element.endDate[0], element.endDate[1]-1, element.endDate[2], element.endDate[3], element.endDate[4]),
                      id : index,
                      employeeScheduleId : element.employeeScheduleId,
                      serviceId : element.bServiceId
                    }
                  )
                );

                this.setState({
                  data : tempData
                });
              }).catch((error) => {
                  this.setState({
                      errorMsg : error.response.data.message
                  });
              });

      var tempData = [];
      var service = [];
      await api.get('bService/getAllBServices')
      .then((response) => {
          tempData = response.data;
      }).catch((error) => {
          this.setState({
              errorMsg : error.response.data.message
          });
      });
      tempData.forEach((element, index) => 
        service[index] = {id : element.id, text : element.name}
        )
      var tempLay = ({ onFieldChange, appointmentData, ...restProps }) => {
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
              availableOptions={service}
              onValueChange={onCustomFieldChange}
              placeholder="Custom field"
            />
          </AppointmentForm.BasicLayout>
        );
      };
      this.setState({
        baseLayout : tempLay
      })
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
    /* const {
      currentDate, data, addedAppointment, appointmentChanges, editingAppointment,
    } = this.state; */
    return (
        <React.Fragment>
            <Paper>
                <Scheduler
                data={this.state.data}
                height={750}
                >
                <ViewState
                    currentDate={this.state.currentDate}
                />
                <EditingState
                    onCommitChanges={this.commitChanges}

                    addedAppointment={this.state.addedAppointment}
                    onAddedAppointmentChange={this.changeAddedAppointment}

                    appointmentChanges={this.state.appointmentChanges}
                    onAppointmentChangesChange={this.changeAppointmentChanges}

                    editingAppointment={this.state.editingAppointment}
                    onEditingAppointmentChange={this.changeEditingAppointment}
                />
                <MonthView
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
                    basicLayoutComponent={this.state.baseLayout}
                    textEditorComponent={TextEditor}
                    selectComponent={ong}
                    messages={messages}
                />
                </Scheduler>
            </Paper>
            <Button id="submitForm" variant="primary" onClick={this.saveSchedule}>Save Changes</Button>
        </React.Fragment>
    );
  }
}
export default Schedule;