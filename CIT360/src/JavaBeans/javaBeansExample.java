/*
  The following examples of code were pulled from Java - The Complete Reference (9th Edition), pages 1208-1209. The commentary is my own. 
  In this example, the author has created three different classes that work in harmony to convey the entire example.
*/

/*
  Class/Bean 1 - "Colors"
  This is the actual bean that does the real work. In this example, this is the only method with private methods the other two are ancillary classes to support the bean, one for serilisazion
*/

//Your basic imports. Note the Serializable import, which will grant persistence to this bean.
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

//The basic constructor of the method, extending Canvas and implementing Serializable
public class Colors extends Canvas implements Serializable {
  
  //the variables that will be needed, color. Note the transient property, explicitly stating that this value will not be saved.
  transient private Color color; // not persistent
  
  //one private portion of this bean, which is persistent via Serializable.
  private boolean rectangular; // is persistent

//A public methods visible in this bean. Note the basic getters/setters.
  public Colors() {
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent me) {
        change();
       }
    });
    rectangular = false;
    setSize(200, 100);
    change();
  }
  
  public boolean getRectangular() {
    return rectangular;
  }
  
  public void setRectangular(boolean flag) {
    this.rectangular = flag;
      repaint();
  }
  
  public void change() {
    color = randomColor();
    repaint();
  }
  
//This is the 'secret sauce of the bean', which churns out a random set of RGB numbers to change the color of the rectangle. In the real world this would be the proprietary portion of the code, such as an algorithm for determining price, or . This would not be visible to the average user of the JavaBean.

  private Color randomColor() {
    int r = (int)(255*Math.random());
    int g = (int)(255*Math.random());
    int b = (int)(255*Math.random());
    return new Color(r, g, b);
  }
  
//The remainder of his example, not really important to this discussion. 
  public void paint(Graphics g) {
    Dimension d = getSize();
    int h = d.height;
    int w = d.width;
    g.setColor(color);
    if(rectangular) {
     g.fillRect(0, 0, w-1, h-1);
    }
    else {
      g.fillOval(0, 0, w-1, h-1);
    }
  }
}

/*
  Class/Bean 2 - "ColorsBeanInfo"
  A fairly straightforward class with the publicly facing details about the bean. Again, where things aren't explicitly stated otherwise, the introspection feature will look into the bean for the information necessary to successfully use the bean. Here we're overriding getPropertyDescriptors() to control what is shared with the end user.
*/

  // A Bean information class.
import java.beans.*;
public class ColorsBeanInfo extends SimpleBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor rectangular = new
        PropertyDescriptor("rectangular", Colors.class);
      PropertyDescriptor pd[] = {rectangular};
      return pd;
    }
    catch(Exception e) {
      System.out.println("Exception caught. " + e);
    }
    return null;
  }
}

/*
  Class/Bean 3 - "IntrospectionDemo"
  This is a completely optional portion of the code included to demonstrate introspection. Again all public, this is a support tool for the main bean. 
*/

// Show properties and events.
  import java.awt.*;
  import java.beans.*;
  
public class IntrospectorDemo {
  public static void main(String args[]) {
    try {
      Class<?> c = Class.forName("Colors");
      BeanInfo beanInfo = Introspector.getBeanInfo(c);
    
    //Since we've overridden the default PropertyDescriptor, this is our version that leaves out the sensitive details we don't want to share. Anything we choose to share is printed to the console.
    
      System.out.println("Properties:");
      PropertyDescriptor propertyDescriptor[] =
        beanInfo.getPropertyDescriptors();
      for(int i = 0; i < propertyDescriptor.length; i++) {
        System.out.println("\t" + propertyDescriptor[i].getName());
      }
      
    //We didn't override the event descriptors, so this portion will print out all of the events we're listening for. In the real world, it may be worth hiding the inputs you're watching for to make reverse engineering the code more difficult.
      System.out.println("Events:");
      EventSetDescriptor eventSetDescriptor[] =
        beanInfo.getEventSetDescriptors();
      for(int i = 0; i < eventSetDescriptor.length; i++) {
        System.out.println("\t" + eventSetDescriptor[i].getName());
    }
    }
    catch(Exception e) {
      System.out.println("Exception caught. " + e);
    }
  }
}


