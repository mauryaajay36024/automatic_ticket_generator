package automatictg.clients;
import java.util.Scanner;
import automatictg.core.Slot;
import automatictg.core.Vehicle;

public class InMemoryClient extends BaseClient {
    Scanner sc=new Scanner(System.in);
    Slot slot=new Slot(1,2);

    public void vehicleEntry() {
        boolean flag=false;
        Vehicle vehicle=availableSlot();
        if(vehicle instanceof Vehicle ){
            for (int i = 0; i < slot.getParkingSlots().length; i++){
                if(slot.getParkingSlots()[i]==null) {
                    slot.getParkingSlots()[i] = vehicle;
                    // Now print Ticket
                    System.out.println("______Ticket_____");
                    System.out.println(slot.getParkingSlots()[i]);
                    System.out.println("Vehicle slot number :" + (i + 1));
                    flag=true;
                    break;
                }
            }
            if (!flag) {
                System.out.println("Parking is full");
            }
        }
    }
    public void vehicleExit(){
        boolean vehicleFound=false;
        System.out.print("Enter vehicle Registration Number :");
        String regNo=sc.nextLine();
        for (int i = 0; i < Slot.parkingSlots.length; i++) {
            if(Slot.parkingSlots[i] !=null && Slot.parkingSlots[i].getRegNo().equalsIgnoreCase(regNo)) {
                Slot.parkingSlots[i]=null;
                System.out.println("Slot Number "+(i+1)+" is now Available ");
                vehicleFound=true;
                break;
            }
        }
        if(!vehicleFound) {
            System.out.println("Vehicle Not Found !!!");
        }
    }
    public void searchByRegNo() {
        boolean vehicleFound=false;
        System.out.print("Enter Registration no to search vehicle Slot :");
        String regNo=sc.nextLine();
        for (int i = 0; i < Slot.parkingSlots.length; i++) {
            if(Slot.parkingSlots[i]!=null && Slot.parkingSlots[i].getRegNo().equalsIgnoreCase(regNo)) {
                System.out.println("Slot Number is "+(i+1));
                vehicleFound=true;
                break;
            }
        }
        if(!vehicleFound) {
            System.out.println("Vehicle Not Found !!!");
        }
    }
    public void regNumberByColour() {
        boolean vehicleFound=false;
        System.out.print("Enter Colour to find Registration numbers of all cars of a particular color :");
        String colour=sc.nextLine();
        for (int i = 0; i < Slot.parkingSlots.length; i++) {
            if(Slot.parkingSlots[i]!=null && Slot.parkingSlots[i].getColour().equalsIgnoreCase(colour)) {
                System.out.println("Registration Number : "+Slot.parkingSlots[i].getRegNo());
                vehicleFound=true;
            }
        }
        if(!vehicleFound) {
            System.out.println("No Vehicle Available with " +colour+ " Colour !!!");
        }
    }
    public void slotByColour() {
        boolean vehicleFound=false;
        System.out.print("Enter Colour to find slot numbers of all Vehicle of a particular color is parked: ");
        String colour=sc.nextLine();
        for (int i = 0; i < Slot.parkingSlots.length; i++) {
            if(Slot.parkingSlots[i]!=null && Slot.parkingSlots[i].getColour().equalsIgnoreCase(colour)) {
                System.out.println("Slot Number : "+(i+1));
                vehicleFound=true;
            }
        }
        if(!vehicleFound) {
            System.out.println("No Vehicle Available with " +colour+ " Colour !!!");
        }
    }
    public Vehicle availableSlot() {
        Vehicle vehicle = registerVehicle();
        if (vehicle instanceof Vehicle) {
            for (int i = 0; i < slot.getParkingSlots().length; i++) {
                //To check if entered registration no is already parked
                if (slot.getParkingSlots()[i] != null && slot.getParkingSlots()[i].getRegNo().equalsIgnoreCase(vehicle.getRegNo())) {
                    System.out.println("Duplicate Vehicle Info");
                    return null;
                } else {
                    return vehicle;
                }
            }

        }
        System.out.println("Invalid Vehicle Info");
        return null;
    }
}
