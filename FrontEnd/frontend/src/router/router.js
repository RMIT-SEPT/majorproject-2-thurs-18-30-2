import FormTemplate from '../components/utils/FormTemplate';
import Home from '../components/Home';
import AboutUs from "../components/AboutUs";
import ContactUs from "../components/ContactUs";
import Profile from '../components/Profile';
import BookingsPage from '../components/BookingPage';
import EmployeesPage from '../components/EmployeesPage';


const router = [
    {
        path: "/home",
        component : Home,
        routes : [
        
        ]
    },
    {
        path: "/about-us",
        component : AboutUs,
        routes : [

        ]
    },
    {
        path: "/contact-us",
        component : ContactUs,
        routes : [

        ]
    },
{
    path : "/log-in",
    component : FormTemplate,
    style : "centered",
    form : "logIn"
},
{
    path : "/sign-up",
    component : FormTemplate,
    style : "centered", 
    form : "signUp"
},
{
    path : "/add-employee",
    component : FormTemplate,
    style : "centered", 
    form : "employeeSignUp"
},
{
    path : "/employee/:eId",
    component : Profile
},
{
    path : "/edit/employee/:eId",
    component : FormTemplate,
    style : "centered",
    form : "editEmployeeProfile"
},
{
    path : "/edit",
    component : FormTemplate,
    style : "centered",
    form : "editProfile"
},
{
    path : "/bookings",
    component : BookingsPage
},
{
    path : "/employees",
    component : EmployeesPage
},
{
    path : "/profile",
    component : Profile
}
];

export default router;