import FormTemplate from '../components/utils/FormTemplate';
import Home from '../components/Home';
import AboutUs from "../components/AboutUs";
import ContactUs from "../components/ContactUs";
import BookingPage from '../components/BookingPage';
import BookingCard from '../components/BookingCard';
import EmployeeCard from '../components/EmployeeCard';
import CardsListPage from '../components/CardsListPage';
import Profile from '../components/Profile';
import BookingForm from '../components/BookingForm';
import Schedule from '../components/Schedule';

// This array contains all our routes.
// In a sense we define our paths globally in this file as an array.
// the indexes are used by component SubRouter.
// This should later be modified so that certain routes will only be
// available to specific users.
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
        component : BookingPage,
        routes : [
            
            {
                path : "/bookings/present",
                label : "Current Bookings",
                component : CardsListPage,
                listApi : null,
                card : BookingCard
            },
            {
                path : "/bookings/past",
                label : "Past Bookings",
                component : CardsListPage,
                listApi : null,
                card : BookingCard
            }
        ]
    },
    {
        path : "/employees",
        component : CardsListPage,
        listApi : "/employee/getAllEmployees",
        card : EmployeeCard
    },
    {
        path : "/profile",
        component : Profile
    },
    {
        path : "/booking-form",
        component : BookingForm
    },
    {
        path : "/schedule/:eId",
        component : Schedule
    }
];

export default router;