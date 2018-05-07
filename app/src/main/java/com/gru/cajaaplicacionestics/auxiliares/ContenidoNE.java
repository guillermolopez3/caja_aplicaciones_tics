package com.gru.cajaaplicacionestics.auxiliares;

import com.gru.cajaaplicacionestics.model.ModelPost;

import java.util.ArrayList;
import java.util.List;

public class ContenidoNE
{
    public static List<ModelPost> cronograma()
    {
        List<ModelPost> array = new ArrayList<>();
        ModelPost post = new ModelPost(1,"Cronograma 2018","Conocé en qué fechas se realizarán las jornadas institucionales de todos los niveles y modalidades",
                "",4,"","http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/2018/CRONOGRAMA2018.pdf");
        array.add(post);

        return array;
    }

    public static List<ModelPost> jornadas()
    {
        List<ModelPost> array = new ArrayList<>();
        ModelPost post = new ModelPost(1,"ORGANIZADOR DE JORNADAS INSTITUCIONALES","¿Qué temas se tratarán en cada jornadas? \n" +
                "¿Qué actividades realizaremos durante las jornadas?\n" +
                "¿Cuáles son las lecturas que debo llevar hechas?\n",
                "",4,"","http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/2018/OrganizadorProgramaporCohorte2018.pdf");
        array.add(post);

        return array;
    }

    public static List<ModelPost> orientacion()
    {
        List<ModelPost> array = new ArrayList<>();
        ModelPost post = new ModelPost();
        post.setId(1);
        post.setTitle("Orientaciones administrativas 2018");
        post.setCopete("Orientacion administrativa sobre la jornada");
        post.setImage("");
        post.setId_tipo_activity(4);
        post.setDescription("");
        post.setLink("http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/OrientacionesAdm/Orientaciones-administrativas-2018.pdf");
        array.add(post);

        post = new ModelPost();
        post.setId(2);
        post.setTitle("Planilla de asistencia - Jornadas Institucionales");
        post.setCopete("Planillas de asistencia");
        post.setImage("");
        post.setId_tipo_activity(4);
        post.setDescription("");
        post.setLink("http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/OrientacionesAdm/PlanillaAsistenciaJI2018.pdf");
        array.add(post);

        post = new ModelPost();
        post.setId(3);
        post.setTitle("Planilla de asistencia - Círculo de Directores");
        post.setCopete("Planillas de asistencia");
        post.setImage("");
        post.setId_tipo_activity(4);
        post.setDescription("");
        post.setLink("http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/OrientacionesAdm/PlanillaAsistenciaCD2018.pdf");
        array.add(post);

        post = new ModelPost();
        post.setId(4);
        post.setTitle("Memo 69/16 - Cambio de actividad");
        post.setCopete("Memo");
        post.setImage("");
        post.setId_tipo_activity(4);
        post.setDescription("");
        post.setLink("http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/OrientacionesAdm/Memo-69-16-PNNE.pdf");
        array.add(post);

        post = new ModelPost();
        post.setId(5);
        post.setTitle("Memo 84/2016");
        post.setCopete("Memo");
        post.setImage("");
        post.setId_tipo_activity(4);
        post.setDescription("");
        post.setLink("http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/OrientacionesAdm/Memo-84-16-PNNE.pdf");
        array.add(post);

        post = new ModelPost();
        post.setId(6);
        post.setTitle("Hoja de ruta del tutor");
        post.setCopete("Hoja de ruta");
        post.setImage("");
        post.setId_tipo_activity(4);
        post.setDescription("");
        post.setLink("http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/OrientacionesAdm/HojaRutaTutor2018.pdf");
        array.add(post);

        return array;
    }

    public static List<ModelPost> recomendaciones()
    {
        List<ModelPost> array = new ArrayList<>();
        ModelPost post = new ModelPost();
        post.setId(1);
        post.setTitle("Tercera Jornada Institucional 2018");
        post.setCopete("");
        post.setImage("");
        post.setId_tipo_activity(4);
        post.setDescription("");
        post.setLink("http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/2018/Recomendaciones%203%20JI-2018.pdf");
        array.add(post);

        post = new ModelPost();
        post.setId(2);
        post.setTitle("Primera y Segunda Jornada Institucional 2018");
        post.setCopete("");
        post.setImage("");
        post.setId_tipo_activity(4);
        post.setDescription("");
        post.setLink("http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/publicaciones/PNFP/Eje1/2018/Recomendaciones%201%20y%202%20JI-2018.pdf");
        array.add(post);

        return array;
    }
}
