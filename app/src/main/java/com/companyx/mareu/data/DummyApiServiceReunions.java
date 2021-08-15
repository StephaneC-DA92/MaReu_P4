package com.companyx.mareu.data;

import com.companyx.mareu.model.DateHeure;
import com.companyx.mareu.model.Reunion;
import com.companyx.mareu.model.Salle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeurSteph on 17/05/2021
 */
public class DummyApiServiceReunions implements ApiServiceReunions {

    private List<Reunion> mReunions = GenerateurReunions.generateMeetings();

    private Map<String, Date> mCatalogueDate;

    @Override
    public List<Reunion> getListeReunions() {
        return mReunions;
    }

    @Override
    public void addReunionItem(Reunion reunion) {
        mReunions.add(reunion);
    }

    @Override
    public void deleteReunionItem(Reunion reunion) {
        mReunions.remove(reunion);
    }

    //Pattern Etat
    @Override
    public List<Reunion> filterPlace(List<Reunion> reunions, List<Salle> salles) {
        List<Reunion> reunionsFiltreesLieu = new ArrayList<Reunion>();
        for (Salle salle : salles) {
            for (Reunion reunion : reunions) {
                if (reunion.getSalle().getLieu() == salle.getLieu()) {
                    reunionsFiltreesLieu.add(reunion);
                }
            }
        }
        return reunionsFiltreesLieu;
    }

    @Override
    public List<Reunion> filterDate(List<Reunion> reunions, Date date) {
        List<Reunion> reunionsFiltreesSalle = new ArrayList<Reunion>();
        for (Reunion reunion : reunions) {

            DateHeure dh = new DateHeure();
            dh.convertDateTimeToDateString(reunion.getHeureDebut());
            Date datedebut = new DateHeure(dh.convertDateTimeToDateString(reunion.getHeureDebut())).formatParseDate();

            if (datedebut.compareTo(date) == 0) {
                reunionsFiltreesSalle.add(reunion);
            }
        }
        return reunionsFiltreesSalle;
    }

    @Override
    public List<Reunion> filterPlaceDate(List<Reunion> reunions, List<Salle> salles, Date date) {
        return filterDate(filterPlace(reunions, salles), date);
    }

    @Override
    public List<Reunion> sortPlaceUp(List<Reunion> reunions) {
//        initial capacity of ten new ArrayList<Reunion>()
        List<Reunion> reunionsTriees = new ArrayList<>(reunions);
        for (int i = 0; i < reunionsTriees.size() - 1; i++) {
            for (int j = 0; j < reunionsTriees.size() - 1 - i; j++) {
                if (reunionsTriees.get(j).getSalle().getLieu().compareTo(reunionsTriees.get(j + 1).getSalle().getLieu()) > 0) {
                    Collections.swap(reunionsTriees, j, j + 1);
                }
            }
        }
        return reunionsTriees;
    }

    @Override
    public List<Reunion> sortPlaceDown(List<Reunion> reunions) {
        List<Reunion> reunionsTriees = new ArrayList<>(reunions);
        for (int i = 0; i < reunionsTriees.size() - 1; i++) {
            for (int j = 0; j < reunionsTriees.size() - 1 - i; j++) {
                if (reunionsTriees.get(j).getSalle().getLieu().compareTo(reunionsTriees.get(j + 1).getSalle().getLieu()) < 0) {
                    Collections.swap(reunionsTriees, j, j + 1);
                }
            }
        }
        return reunionsTriees;
    }

    @Override
    public List<Reunion> sortTimeUp(List<Reunion> reunions) {
        List<Reunion> reunionsTriees = new ArrayList<>(reunions);
        for (int i = 0; i < reunionsTriees.size() - 1; i++) {
            for (int j = 0; j < reunionsTriees.size() - 1 - i; j++) {
                if (reunionsTriees.get(j).getHeureDebut().compareTo(reunionsTriees.get(j + 1).getHeureDebut()) < 0) {
                    Collections.swap(reunionsTriees, j, j + 1);
                }
            }
        }
        return reunionsTriees;
    }

    @Override
    public List<Reunion> sortTimeDown(List<Reunion> reunions) {
        List<Reunion> reunionsTriees = new ArrayList<>(reunions);
        for (int i = 0; i < reunionsTriees.size() - 1; i++) {
            for (int j = 0; j < reunionsTriees.size() - 1 - i; j++) {
                if (reunionsTriees.get(j).getHeureDebut().compareTo(reunionsTriees.get(j + 1).getHeureDebut()) > 0) {
                    Collections.swap(reunionsTriees, j, j + 1);
                }
            }
        }
        return reunionsTriees;
    }

    public String[] getListeDate(List<Reunion> reunions) {
        List<Reunion> reunionst = sortTimeDown(reunions);
        int n = reunionst.size();
        List<String> lChoix = new ArrayList<String>();

        DateHeure dh = new DateHeure();

        lChoix.add(dh.convertDateTimeToDateString(reunionst.get(0).getHeureDebut()));
        if (reunionst.size() > 1) {
            for (int i = 1; i < reunionst.size(); i++) {
                if (dh.convertDateTimeToDateString(reunionst.get(i).getHeureDebut()).compareTo(dh.convertDateTimeToDateString(reunionst.get(i - 1).getHeureDebut())) == 0) {
                    n--;
                    continue;
                }
                lChoix.add(dh.convertDateTimeToDateString(reunionst.get(i).getHeureDebut()));
            }
        }

        String[] choix = new String[n + 1];
        lChoix.add(0, "");
        lChoix.toArray(choix);
        return choix;
    }
}
