package india.abhijeet.k.anylearn;

public class Uploading {

private String vName;
private String vVideoUrl;
    private String vViews;



public Uploading()
{

// Empty Constructor needed


}


    public Uploading(String name,String videourl,String Vdviews)
    {
           if (name.trim()=="")
           {

               name="No Name";
           }
           if (Vdviews.trim()=="")
           {
               Vdviews="0";
           }

        vName=name;
        vVideoUrl=videourl;
        vViews=Vdviews;

    }


       public String getName()
       {

         return vName;
       }

    public void setName(String name) {

    vName=name;

    }


    public String getviews()
    {

        return vViews;
    }

    public void setviews(String views) {

        vViews=views;

    }





    public String getvVideoUrl() {

    return vVideoUrl;
    }

    public void setvVideoUrl(String videoUrl) {

    vVideoUrl=videoUrl;


    }
}
