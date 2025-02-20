import { ContactSupportRounded, 
    InfoRounded,
    AccountCircleRounded,
    LibraryBooksRounded,
    GroupRounded,
    PersonAddRounded,
    BusinessRounded,
    CalendarToday,
    InsertInvitationRounded } from '@material-ui/icons';

// This class is similar to form-templates.js as it provides a structure for the 
// dynamic component SideBar
export default class SideBarMenu {
    employeeMenu() {
        var items = [
            
        ];
        items.push(...this.general());
        items.push(...this.guestMenu());
        return items;    
    }

    customerMenu() {
        var items = [
            
        ];
        items.push(...this.general());
        items.push(...this.guestMenu());
        return items;
    }

    ownerMenu() {
        var items = [];
        items.push(...this.general());
        items.push(...[
            { 
                name : 'employees', 
                label : 'Employees',
                path : '/employees',
                icon : GroupRounded
            },
            {
                name : 'add-employees', 
                label : 'Add Employee',
                path : '/add-employee',
                icon : PersonAddRounded
            }
        ]);
        items.push(...this.guestMenu());
        return items;
    }

    guestMenu() {
        return [
            { 
                name : 'contactUs', 
                label : 'Contact Us',
                path : '/contact-us',
                icon : ContactSupportRounded
            },
            {
                name : 'aboutUs',
                label : 'About Us',
                path : '/about-us',
                icon :  InfoRounded
            }
        ]
    }

    general() {
        return [
            { 
                name : 'profile', 
                label : 'Profile',
                path : '/profile',
                icon : AccountCircleRounded
            },
            {
                name : 'makeBooking',
                label : 'Make Booking',
                path : '/booking-form',
                icon :  InsertInvitationRounded
            },
            {
                name : 'bookings',
                label : 'Bookings',
                path : '/bookings',
                icon :  LibraryBooksRounded
            }
        ];
    }
}