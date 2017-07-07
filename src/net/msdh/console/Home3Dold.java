package net.msdh.console;


import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.pickfast.PickCanvas;
import com.sun.j3d.utils.universe.SimpleUniverse;


import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public class Home3Dold extends MouseAdapter{

  protected Canvas3D canvas3D;
  protected SimpleUniverse u = null;
  protected BranchGroup scene = null;
  protected Scene s;
  protected float eyeOffset =0.00F;
  protected int CanvasH=600;
  protected int CanvasW=800;
  protected TransformGroup mytg;
  protected PickCanvas pickCanvas;
  protected Shape3D newShare;


  public Canvas3D getCanvas3D(){
    return canvas3D;
  }

  public Home3Dold(int w, int h){

    canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    canvas3D.setSize(w, h);

    u = new SimpleUniverse(canvas3D);
    scene = createSceneGraph(0);
    u.getViewingPlatform().setNominalViewingTransform();
    u.addBranchGraph(scene);

    pickCanvas = new PickCanvas(canvas3D, scene);
    pickCanvas.setMode(PickCanvas.TYPE_SHAPE3D);

    canvas3D.addMouseListener(this);
  }

  public Home3Dold(){
    canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    canvas3D.setSize(CanvasW, CanvasH);
    u = new SimpleUniverse(canvas3D);
    scene = createSceneGraph(0);
    u.getViewingPlatform().setNominalViewingTransform();
    u.addBranchGraph(scene);
  }

  public void destroy() {
    u.removeAllLocales();
  }


  public BranchGroup createSceneGraph(int i) {

    BranchGroup objRoot = new BranchGroup();
    try{
	        Transform3D myTransform3D = new Transform3D();
	        myTransform3D.setTranslation(new Vector3f(0.0f,0.0f,0.0f));

	        TransformGroup objTrans = new TransformGroup(myTransform3D);
            objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

            Transform3D t = new Transform3D();
	        TransformGroup tg = new TransformGroup(t);
	        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	        objTrans.addChild(tg);

            ObjectFile f = new ObjectFile();
            f.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
            System.out.println("Loading scene ...");

            s = f.load("Resources/Objects/myhouse.obj");

            Transform3D myTrans = new Transform3D();
            myTrans.setTranslation(new Vector3f(0.0f, 0.0f, 0.0F));
            mytg = new TransformGroup(myTrans);

            mytg.addChild(s.getSceneGroup());

            tg.addChild(mytg);

            Hashtable table = s.getNamedObjects();

            for (Enumeration e = table.keys() ; e.hasMoreElements() ;) {
              Object key = e.nextElement();
              System.out.println(key);
              Object obj = table.get(key);
              //System.out.println(obj.getClass().getName());
              Shape3D shape  = (Shape3D)obj;
              shape.setName(key.toString());
              shape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
              shape.setCapability(Shape3D.ALLOW_PICKABLE_WRITE);

              //Appearance ap = new Appearance();
              //Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
              //Color3f red = new Color3f(0.7f, .0f, .15f);
              //Color3f green = new Color3f(0f, .7f, .15f);

              if(key.equals("vnesnie_sten_")){

                 Appearance ar = new Appearance();

                 TextureLoader loader = new TextureLoader("Resources/Textures/brick.jpg","LUMINANCE", new Container());
                 Texture texture = loader.getTexture();
                 texture.setBoundaryModeS(Texture.WRAP);
                 texture.setBoundaryModeT(Texture.WRAP);
                 texture.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );


                 TextureAttributes texAttr = new TextureAttributes();
                 texAttr.setTextureMode(TextureAttributes.MODULATE);

                 ar.setTexture(texture);
                 ar.setTextureAttributes(texAttr);

                 shape.setAppearance(ar);

                // newShare = new Shape3D(shape.getGeometry(),ar);

                // newShare.setName("NewShare");
                // newShare.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

                 //mytg.addChild(newShare);

                //canvas3D.repaint();
              }




              //float transparencyValue = -1.0f;
             // TransparencyAttributes t_attr = new TransparencyAttributes(TransparencyAttributes.BLENDED,
             //   transparencyValue,TransparencyAttributes.BLEND_SRC_ALPHA,TransparencyAttributes.BLEND_ONE);

             // ap.setTransparencyAttributes(t_attr);
              //ap.setRenderingAttributes(new RenderingAttributes());
              //bg.addChild(ap);

              //System.out.println("share: "+shape.getName());
            }

        System.out.println( "Finished Loading" );

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

        Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f light1Direction  = new Vector3f(4.0f, -9.0f, -12.0f);

        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objTrans.addChild(light1);

        Background background = new Background(new Color3f(1f,1f,1f));
      //  BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        //background.
        background.setApplicationBounds(bounds);

        objTrans.addChild(background);


       // background.

          //    shape.setAppearance(ap);


	            // Set up the ambient light
      //  Color3f ambientColor = new Color3f(1.0f, .4f, 0.3f);
      //  AmbientLight ambientLightNode = new AmbientLight(ambientColor);
      //  ambientLightNode.setInfluencingBounds(bounds);
      //  objTrans.addChild(ambientLightNode);

//        MouseZoom behaviorClick = new MouseZoom();
//        behaviorClick.setTransformGroup(tg);
//	    objTrans.addChild(behaviorClick);

        MouseRotate behavior = new MouseRotate();
	    behavior.setTransformGroup(tg);
	    objTrans.addChild(behavior);
	      // Create the translate behavior node

        MouseWheelZoom zoom = new MouseWheelZoom();
        zoom.setTransformGroup(tg);
        objTrans.addChild(zoom);

	    MouseTranslate behavior3 = new MouseTranslate();
	    behavior3.setTransformGroup(tg);
	    objTrans.addChild(behavior3);

	    behavior3.setSchedulingBounds(bounds);

	    KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(tg);
		keyNavBeh.setSchedulingBounds(new BoundingSphere(new Point3d(),1000.0));
		objTrans.addChild(keyNavBeh);

	    behavior.setSchedulingBounds(bounds);

        zoom.setSchedulingBounds(bounds);

        objRoot.addChild(objTrans);

        }
        catch(Throwable t){
          System.out.println("Error: "+t);
        }
        return objRoot;
    }

  public Hashtable getObjectsName(){
     return s.getNamedObjects();
  }

  public void setUnvisible(String objName){
    Shape3D shape;
    try{

      Object obj = s.getNamedObjects().get(objName);
      System.out.println(objName);
      shape  = (Shape3D)obj;
      shape.setPickable(false);

      Appearance ap = new Appearance();
      ap.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED,100.0f,TransparencyAttributes.BLEND_SRC_ALPHA,TransparencyAttributes.BLEND_ONE));
      ap.setRenderingAttributes(new RenderingAttributes());

      shape.setAppearance(ap);

      System.out.println("set unvisible");
    }
    catch(Throwable t){
      System.out.println("Error: "+t);
    }
  }


  public void setVisible(String objName){
    Shape3D shape;
    try{

      Object obj = s.getNamedObjects().get(objName);
      System.out.println(objName);
      shape  = (Shape3D)obj;
      System.out.println("shape iz object vibrano");

      shape.setPickable(false);

      float transparencyValue = 100.0f;
      TransparencyAttributes t_attr = new TransparencyAttributes(TransparencyAttributes.BLENDED,transparencyValue,TransparencyAttributes.BLEND_SRC_ALPHA,TransparencyAttributes.BLEND_ONE);
      Appearance ap = new Appearance();
      ap.setTransparencyAttributes(t_attr);
      ap.setRenderingAttributes(new RenderingAttributes());
      shape.setAppearance(ap);

      System.out.println("set visible");
    }
    catch(Throwable t){
      System.out.println("Error: "+t);
    }
  }

  public void deleteShape(String objName){
      try{
        Object obj = s.getNamedObjects().get(objName);
        System.out.println(objName);
        Shape3D shape = (Shape3D)obj;
        shape.removeAllGeometries();
      }
      catch(Throwable t){
        System.out.println("Error: "+t);
      }
    }



    public void mouseClicked(MouseEvent e) {

      pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
      pickCanvas.setShapeLocation(e);
      pickCanvas.setFlags(PickInfo.ALL_GEOM_INFO|PickInfo.NODE);

      PickInfo result = pickCanvas.pickClosest();

      if(result != null){

        System.out.println("button: " + e.getButton());
        System.out.println(result.getNode().getClass().getName());
        if(result.getNode() instanceof Shape3D){

          Color3f color;
          Appearance app= new Appearance();
          if(e.getButton()==1){
            color= new Color3f(Color.red);
          }
          else{
            color= new Color3f(Color.green);
          }
          Color3f black= new Color3f(0.0f,0.0f,0.f);

          Color3f white= new Color3f(1.0f,1.0f,1.0f);
          app.setMaterial(new Material(color,black,color,white,70f));

          Shape3D temps = (Shape3D) result.getNode();

          //temps.setUserData();
          System.out.println("select shape: " + temps.getName());
          temps.setAppearance(app);

         // try {
            //Connection cnn = new Connection();
           // cnn.Send("127.0.0.1",60000,"test dev name: " + temps.getName()+" action: " + (e.getButton()==1?"on":"off"));
           // cnn.CloseClient();
        //  }
        //  catch (IOException e1) {
        //        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        //  }

//        canvas3D.repaint();
        }
      }
      else{
        System.out.println("Nothing picked");
      }
    }
}