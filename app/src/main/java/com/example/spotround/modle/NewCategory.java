package com.example.spotround.modle;

import android.icu.text.RelativeDateTimeFormatter;
import android.text.PrecomputedText;

public class NewCategory {
    int gopens, gobcs, gscs, gsts, gvjs, gnt1s, gnt2s, gnt3s, lopens, lobcs, lscs, lsts, lvjs, lnt1s, lnt2s, lnt3s;

    public NewCategory(){}

    public NewCategory(int gopens, int gobcs, int gscs, int gsts, int gvjs, int gnt1s, int gnt2s, int gnt3s, int lopens, int lobcs, int lscs, int lsts, int lvjs, int lnt1s, int lnt2s, int lnt3s) {
        this.gopens = gopens;
        this.gobcs = gobcs;
        this.gscs = gscs;
        this.gsts = gsts;
        this.gvjs = gvjs;
        this.gnt1s = gnt1s;
        this.gnt2s = gnt2s;
        this.gnt3s = gnt3s;
        this.lopens = lopens;
        this.lobcs = lobcs;
        this.lscs = lscs;
        this.lsts = lsts;
        this.lvjs = lvjs;
        this.lnt1s = lnt1s;
        this.lnt2s = lnt2s;
        this.lnt3s = lnt3s;
    }

    public int getGopens() {
        return gopens;
    }

    public void setGopens(int gopens) {
        this.gopens = gopens;
    }

    public int getGobcs() {
        return gobcs;
    }

    public void setGobcs(int gobcs) {
        this.gobcs = gobcs;
    }

    public int getGscs() {
        return gscs;
    }

    public void setGscs(int gscs) {
        this.gscs = gscs;
    }

    public int getGsts() {
        return gsts;
    }

    public void setGsts(int gsts) {
        this.gsts = gsts;
    }

    public int getGvjs() {
        return gvjs;
    }

    public void setGvjs(int gvjs) {
        this.gvjs = gvjs;
    }

    public int getGnt1s() {
        return gnt1s;
    }

    public void setGnt1s(int gnt1s) {
        this.gnt1s = gnt1s;
    }

    public int getGnt2s() {
        return gnt2s;
    }

    public void setGnt2s(int gnt2s) {
        this.gnt2s = gnt2s;
    }

    public int getGnt3s() {
        return gnt3s;
    }

    public void setGnt3s(int gnt3s) {
        this.gnt3s = gnt3s;
    }

    public int getLopens() {
        return lopens;
    }

    public void setLopens(int lopens) {
        this.lopens = lopens;
    }

    public int getLobcs() {
        return lobcs;
    }

    public void setLobcs(int lobcs) {
        this.lobcs = lobcs;
    }

    public int getLscs() {
        return lscs;
    }

    public void setLscs(int lscs) {
        this.lscs = lscs;
    }

    public int getLsts() {
        return lsts;
    }

    public void setLsts(int lsts) {
        this.lsts = lsts;
    }

    public int getLvjs() {
        return lvjs;
    }

    public void setLvjs(int lvjs) {
        this.lvjs = lvjs;
    }

    public int getLnt1s() {
        return lnt1s;
    }

    public void setLnt1s(int lnt1s) {
        this.lnt1s = lnt1s;
    }

    public int getLnt2s() {
        return lnt2s;
    }

    public void setLnt2s(int lnt2s) {
        this.lnt2s = lnt2s;
    }

    public int getLnt3s() {
        return lnt3s;
    }

    public void setLnt3s(int lnt3s) {
        this.lnt3s = lnt3s;
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
                setGopens(getGopens() - 1);
                break;
            case "gobcs":
                setGobcs((getGobcs() - 1));
                break;
            case "gscs":
                setGscs((getGscs() - 1));
                break;
            case "gsts":
                setGsts((getGsts() - 1));
                break;
            case "gvjs":
                setGvjs((getGvjs() - 1));
                break;
            case "gnt1s":
                setGnt1s((getGnt1s() - 1));
                break;
            case "gnt2s":
                setGnt2s((getGnt2s() - 1));
                break;
            case "gnt3s":
                setGnt3s((getGnt3s() - 1));
                break;
            case "lopens":
                setLopens((getLopens() - 1));
                break;
            case "lobcs":
                setLobcs((getLobcs() - 1));
                break;
            case "lscs":
                setLscs((getLscs() - 1));
                break;
            case "lsts":
                setLsts((getLsts() - 1));
                break;
            case "lvjs":
                setLvjs((getLvjs() - 1));
                break;
            case "lnt1s":
                setLnt1s((getLnt1s() - 1));
                break;
            case "lnt2s":
                setLnt2s((getLnt2s() - 1));
                break;
            case "lnt3s":
                setLnt3s((getLnt3s() - 1));
                break;
        }
    }

    public void IncCasteVacancy(String caste) {
        switch(caste) {
            case "gopens":
                setGopens((getGopens() + 1));
                break;
            case "gobcs":
                setGobcs((getGobcs() + 1));
                break;
            case "gscs":
                setGscs((getGscs() + 1));
                break;
            case "gsts":
                setGsts((getGsts() + 1));
                break;
            case "gvjs":
                setGvjs((getGvjs() + 1));
                break;
            case "gnt1s":
                setGnt1s((getGnt1s() + 1));
                break;
            case "gnt2s":
                setGnt2s((getGnt2s() + 1));
                break;
            case "gnt3s":
                setGnt3s((getGnt3s() + 1));
                break;
            case "lopens":
                setLopens((getLopens() + 1));
                break;
            case "lobcs":
                setLobcs((getLobcs() + 1));
                break;
            case "lscs":
                setLscs((getLscs() + 1));
                break;
            case "lsts":
                setLsts((getLsts() + 1));
                break;
            case "lvjs":
                setLvjs((getLvjs() + 1));
                break;
            case "lnt1s":
                setLnt1s((getLnt1s() + 1));
                break;
            case "lnt2s":
                setLnt2s((getLnt2s() + 1));
                break;
            case "lnt3s":
                setLnt3s((getLnt3s() + 1));
                break;
        }
    }

    @Override
    public String toString() {
        return "NewCategory{" +
                "gopens=" + gopens +
                ", gobcs=" + gobcs +
                ", gscs=" + gscs +
                ", gsts=" + gsts +
                ", gvjs=" + gvjs +
                ", gnt1s=" + gnt1s +
                ", gnt2s=" + gnt2s +
                ", gnt3s=" + gnt3s +
                ", lopens=" + lopens +
                ", lobcs=" + lobcs +
                ", lscs=" + lscs +
                ", lsts=" + lsts +
                ", lvjs=" + lvjs +
                ", lnt1s=" + lnt1s +
                ", lnt2s=" + lnt2s +
                ", lnt3s=" + lnt3s +
                '}';
    }
}
