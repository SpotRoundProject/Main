package com.example.spotround.modle;

import android.icu.text.RelativeDateTimeFormatter;
import android.text.PrecomputedText;

public class NewCategory {
    int gopens, gobcs, gscs, gsts, gvjs, gnt1s, gnt2s, gnt3s, lopens, lobcs, lscs, lsts, lvjs, lnt1s, lnt2s, lnt3s;

    public NewCategory(String gopens, String gobcs, String gscs, String gsts, String gvjs, String gnt1s, String gnt2s, String gnt3s, String lopens, String lobcs, String lscs, String lsts, String lvjs, String lnt1s, String lnt2s, String lnt3s) {
        this.gopens = Integer.parseInt(gopens);
        this.gobcs = Integer.parseInt(gobcs);
        this.gscs = Integer.parseInt(gscs);
        this.gsts = Integer.parseInt(gsts);
        this.gvjs = Integer.parseInt(gvjs);
        this.gnt1s = Integer.parseInt(gnt1s);
        this.gnt2s = Integer.parseInt(gnt2s);
        this.gnt3s = Integer.parseInt(gnt3s);
        this.lopens = Integer.parseInt(lopens);
        this.lobcs = Integer.parseInt(lobcs);
        this.lscs = Integer.parseInt(lscs);
        this.lsts = Integer.parseInt(lsts);
        this.lvjs = Integer.parseInt(lvjs);
        this.lnt1s = Integer.parseInt(lnt1s);
        this.lnt2s = Integer.parseInt(lnt2s);
        this.lnt3s = Integer.parseInt(lnt3s);
    }

    public int getGopens() {
        return gopens;
    }

    public void setGopens(String gopens) {
        this.gopens = Integer.parseInt(gopens);
    }

    public int getGobcs() {
        return gobcs;
    }

    public void setGobcs(String gobcs) {
        this.gobcs = Integer.parseInt(gobcs);
    }

    public int getGscs() {
        return gscs;
    }

    public void setGscs(String gscs) {
        this.gscs = Integer.parseInt(gscs);
    }

    public int getGsts() {
        return gsts;
    }

    public void setGsts(String gsts) {
        this.gsts = Integer.parseInt(gsts);
    }

    public int getGvjs() {
        return gvjs;
    }

    public void setGvjs(String gvjs) {
        this.gvjs = Integer.parseInt(gvjs);
    }

    public int getGnt1s() {
        return gnt1s;
    }

    public void setGnt1s(String gnt1s) {
        this.gnt1s = Integer.parseInt(gnt1s);
    }

    public int getGnt2s() {
        return gnt2s;
    }

    public void setGnt2s(String gnt2s) {
        this.gnt2s = Integer.parseInt(gnt2s);
    }

    public int getGnt3s() {
        return gnt3s;
    }

    public void setGnt3s(String gnt3s) {
        this.gnt3s = Integer.parseInt(gnt3s);
    }

    public int getLopens() {
        return lopens;
    }

    public void setLopens(String lopens) {
        this.lopens = Integer.parseInt(lopens);
    }

    public int getLobcs() {
        return lobcs;
    }

    public void setLobcs(String lobcs) {
        this.lobcs = Integer.parseInt(lobcs);
    }

    public int getLscs() {
        return lscs;
    }

    public void setLscs(String lscs) {
        this.lscs = Integer.parseInt(lscs);
    }

    public int getLsts() {
        return lsts;
    }

    public void setLsts(String lsts) {
        this.lsts = Integer.parseInt(lsts);
    }

    public int getLvjs() {
        return lvjs;
    }

    public void setLvjs(String lvjs) {
        this.lvjs = Integer.parseInt(lvjs);
    }

    public int getLnt1s() {
        return lnt1s;
    }

    public void setLnt1s(String lnt1s) {
        this.lnt1s = Integer.parseInt(lnt1s);
    }

    public int getLnt2s() {
        return lnt2s;
    }

    public void setLnt2s(String lnt2s) {
        this.lnt2s = Integer.parseInt(lnt2s);
    }

    public int getLnt3s() {
        return lnt3s;
    }

    public void setLnt3s(String lnt3s) {
        this.lnt3s = Integer.parseInt(lnt3s);
    }

    public int getCasteVacancy(String caste) {
        switch(caste) {
            case "gopens":
                return getGopens();
            case "gobcs":
                return getGobcs();
            case "gscs":
                return getGscs();
            case "gsts":
                return getGsts();
            case "gvjs":
                return getGvjs();
            case "gnt1s":
                return getGnt1s();
            case "gnt2s":
                return getGnt2s();
            case "gnt3s":
                return getGnt3s();
            case "lopens":
                return getLopens();
            case "lobcs":
                return getLobcs();
            case "lscs":
                return getLscs();
            case "lsts":
                return getLsts();
            case "lvjs":
                return getLvjs();
            case "lnt1s":
                return getLnt1s();
            case "lnt2s":
                return getLnt2s();
            case "lnt3s":
                return getLnt3s();
        }
        return 0;
    }

    public void DecCasteVacancy(String caste) {
        switch(caste) {
            case "gopens":
                setGopens((getGopens() - 1) + "");
                break;
            case "gobcs":
                setGobcs((getGobcs() - 1) + "");
                break;
            case "gscs":
                setGscs((getGscs() - 1) + "");
                break;
            case "gsts":
                setGsts((getGsts() - 1) + "");
                break;
            case "gvjs":
                setGvjs((getGvjs() - 1) + "");
                break;
            case "gnt1s":
                setGnt1s((getGnt1s() - 1) + "");
                break;
            case "gnt2s":
                setGnt2s((getGnt2s() - 1) + "");
                break;
            case "gnt3s":
                setGnt3s((getGnt3s() - 1) + "");
                break;
            case "lopens":
                setLopens((getLopens() - 1) + "");
                break;
            case "lobcs":
                setLobcs((getLobcs() - 1) + "");
                break;
            case "lscs":
                setLscs((getLscs() - 1) + "");
                break;
            case "lsts":
                setLsts((getLsts() - 1) + "");
                break;
            case "lvjs":
                setLvjs((getLvjs() - 1) + "");
                break;
            case "lnt1s":
                setLnt1s((getLnt1s() - 1) + "");
                break;
            case "lnt2s":
                setLnt2s((getLnt2s() - 1) + "");
                break;
            case "lnt3s":
                setLnt3s((getLnt3s() - 1) + "");
                break;
        }
    }

    public void IncCasteVacancy(String caste) {
        switch(caste) {
            case "gopens":
                setGopens((getGopens() + 1) + "");
                break;
            case "gobcs":
                setGobcs((getGobcs() + 1) + "");
                break;
            case "gscs":
                setGscs((getGscs() + 1) + "");
                break;
            case "gsts":
                setGsts((getGsts() + 1) + "");
                break;
            case "gvjs":
                setGvjs((getGvjs() + 1) + "");
                break;
            case "gnt1s":
                setGnt1s((getGnt1s() + 1) + "");
                break;
            case "gnt2s":
                setGnt2s((getGnt2s() + 1) + "");
                break;
            case "gnt3s":
                setGnt3s((getGnt3s() + 1) + "");
                break;
            case "lopens":
                setLopens((getLopens() + 1) + "");
                break;
            case "lobcs":
                setLobcs((getLobcs() + 1) + "");
                break;
            case "lscs":
                setLscs((getLscs() + 1) + "");
                break;
            case "lsts":
                setLsts((getLsts() + 1) + "");
                break;
            case "lvjs":
                setLvjs((getLvjs() + 1) + "");
                break;
            case "lnt1s":
                setLnt1s((getLnt1s() + 1) + "");
                break;
            case "lnt2s":
                setLnt2s((getLnt2s() + 1) + "");
                break;
            case "lnt3s":
                setLnt3s((getLnt3s() + 1) + "");
                break;
        }
    }

}
