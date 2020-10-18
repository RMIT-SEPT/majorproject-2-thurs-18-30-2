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
import { connect } from 'react-redux';
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
    //Bind all functions to class
    this.commitChanges = this.commitChanges.bind(this);
    this.changeAddedAppointment = this.changeAddedAppointment.bind(this);
    this.changeAppointmentChanges = this.changeAppointmentChanges.bind(this);
    this.changeEditingAppointment = this.changeEditingAppointment.bind(this);
    this.saveSchedule = this.saveSchedule.bind(this);
    //Set current date
    this.state.currentDate = new Date();
    //Base layout for editing schedules
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
  //Use all buffer information to edit/add respective schedules timing blocks
  saveSchedule() {
    var tempBuffer = this.state.data;
    var convBuffer = [];
    //Create a buffer with the proper information
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
    );
    //Iterate through and make api calls for each block
    convBuffer.forEach(element => 
      api.post('employeeSchedule/editSchedule/' + element.employeeScheduleId, element)
            .then((response) => {
                console.log(response.data);
            }).catch((error) => {
                this.setState({
                    errorMsg : error.response.data.message
                });
            })
    );
  }

  //On component load
  async componentDidMount() {
    //Api call to fill in the schedule to the calendar
    await api.get('employeeSchedule/getSchedules/employee/' + this.state.employeeID)
              .then((response) => {
                var tempData = [];
                //Create a buffer with the proper information
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
      //Api call to get all the services to populate the list for editing the schedule block
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
      //Temporary layout change for adding the aforementioned services
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
  //Adding appointments
  changeAddedAppointment(addedAppointment) {
    this.setState({ addedAppointment });
  }
  //Applying changes
  changeAppointmentChanges(appointmentChanges) {
    this.setState({ appointmentChanges });
  }
  //Editting appointments
  changeEditingAppointment(editingAppointment) {
    this.setState({ editingAppointment });
  }
  //Commit changes to actual data set
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
    if(this.props.user.userDetails) {
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
                  {this.props.user.userDetails.empType === 'admin' && <EditingState
                      onCommitChanges={this.commitChanges}

                      addedAppointment={this.state.addedAppointment}
                      onAddedAppointmentChange={this.changeAddedAppointment}

                      appointmentChanges={this.state.appointmentChanges}
                      onAppointmentChangesChange={this.changeAppointmentChanges}

                      editingAppointment={this.state.editingAppointment}
                      onEditingAppointmentChange={this.changeEditingAppointment}
                  />}
                  <MonthView
                      startDayHour={this.state.startTime}
                      endDayHour={this.state.endTime}
                  />
                  {this.props.user.userDetails.empType === 'admin' && <EditRecurrenceMenu />}
                  {this.props.user.userDetails.empType === 'admin' && <ConfirmationDialog />}
                  <Appointments />
                  {this.props.user.userDetails.empType === 'admin' && <AppointmentTooltip
                      showOpenButton
                      showDeleteButton
                  />}
                  {this.props.user.userDetails.empType === 'admin' && <AppointmentForm 
                      basicLayoutComponent={this.state.baseLayout}
                      textEditorComponent={TextEditor}
                      selectComponent={ong}
                      messages={messages}
                  />}
                  </Scheduler>
              </Paper>
              <Button id="submitForm" variant="primary" onClick={this.saveSchedule}>Save Changes</Button>
          </React.Fragment>
      );
    }
  
  else {
    return(
      <React.Fragment>Loading</React.Fragment>
    );
  }
}
}
//Redux for user state
const mapStateToProps = state => ({
  user : state.user
});

const mapDispatchToProps = () => {
  return { 
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps()
)(Schedule);