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
    path : "/profile",
    component : Profile
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
}
];

export default router;