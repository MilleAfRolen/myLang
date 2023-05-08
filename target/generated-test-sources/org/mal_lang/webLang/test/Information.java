package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public abstract class Information extends Asset {
  public Read read;

  public Write write;

  public Delete delete;

  public Deny deny;

  public Extract extract;

  public OperativeSystem os = null;

  public Information(String name) {
    super(name);
    assetClassName = "Information";
    AttackStep.allAttackSteps.remove(read);
    read = new Read(name);
    AttackStep.allAttackSteps.remove(write);
    write = new Write(name);
    AttackStep.allAttackSteps.remove(delete);
    delete = new Delete(name);
    AttackStep.allAttackSteps.remove(deny);
    deny = new Deny(name);
    AttackStep.allAttackSteps.remove(extract);
    extract = new Extract(name);
  }

  public Information() {
    this("Anonymous");
  }

  public void addOs(OperativeSystem os) {
    this.os = os;
    os.information = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("os")) {
      return OperativeSystem.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("os")) {
      if (os != null) {
        assets.add(os);
      }
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (os != null) {
      assets.add(os);
    }
    return assets;
  }

  public class Read extends AttackStepMin {
    private Set<AttackStep> _cacheParentRead;

    public Read(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentRead == null) {
        _cacheParentRead = new HashSet<>();
        if (os != null) {
          _cacheParentRead.add(os.doStuff);
        }
      }
      for (AttackStep attackStep : _cacheParentRead) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Information.read");
    }
  }

  public class Write extends AttackStepMin {
    private Set<AttackStep> _cacheParentWrite;

    public Write(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentWrite == null) {
        _cacheParentWrite = new HashSet<>();
        if (os != null) {
          _cacheParentWrite.add(os.doStuff);
        }
      }
      for (AttackStep attackStep : _cacheParentWrite) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Information.write");
    }
  }

  public class Delete extends AttackStepMin {
    private Set<AttackStep> _cacheParentDelete;

    public Delete(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDelete == null) {
        _cacheParentDelete = new HashSet<>();
        if (os != null) {
          _cacheParentDelete.add(os.doStuff);
        }
      }
      for (AttackStep attackStep : _cacheParentDelete) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Information.delete");
    }
  }

  public class Deny extends AttackStepMin {
    public Deny(String name) {
      super(name);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Information.deny");
    }
  }

  public class Extract extends AttackStepMin {
    public Extract(String name) {
      super(name);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Information.extract");
    }
  }
}
