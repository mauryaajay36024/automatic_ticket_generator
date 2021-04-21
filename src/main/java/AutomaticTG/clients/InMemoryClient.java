package AutomaticTG.clients;
import java.util.Scanner;
import AutomaticTG.core.Slot;
import AutomaticTG.core.Vehicle;

public class InMemoryClient extends Client{
    Scanner sc=new Scanner(System.in);
    Slot slot=new Slot(5,20);
    public void vehicleEntry() {
        Vehicle vehicle=registerVehicle();
        if(vehicle instanceof Vehicle){
            for (int i = 0; i < slot.getParkingSlots().length; i++){
                if(slot.getParkingSlots()[i]==null) {
                    slot.getParkingSlots()[i]=vehicle;
                    // Now print Ticket
                    System.out.println("______Ticket_____");
                    System.out.println(slot.getParkingSlots()[i]);
                    System.out.println("Vehicle slot number :"+ (i+1));
                    break;
                }
            }
        }
        else{
            System.out.println("Wrong Input");
        }

    }
    public void vehicleExit(){
        boolean vehicleFound=false;
        System.out.print("Enter vehicle Registration Number :");
        String regNo=sc.nextLine();
        for (int i = 0; i < Slot.parkingSlots.length; i++) {
            if(Slot.parkingSlots[i].getRegNo().equals(regNo)) {
                Slot.parkingSlots[i]=null;
                System.out.println("Slot Number "+(i+1)+" is now Available ");
                vehicleFound=true;
                break;
            }
        }
        if(!vehicleFound) {
            System.err.println("Vehicle Not Found !!!");
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
            System.err.println("Vehicle Not Found !!!");
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
            System.err.println("No Vehicle Available with " +colour+ " Colour !!!");
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
            System.err.println("No Vehicle Available with " +colour+ " Colour !!!");
        }
    }
}
