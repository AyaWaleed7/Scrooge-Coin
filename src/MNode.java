
public class MNode {
	public String hash;
	public MNode parent = null;
	public MNode lc = null;
	public MNode rc = null;

	public MNode(String hash) {
		this.hash = hash;
	}

	public MNode getParent() {
		return parent;
	}

	public void setParent(MNode parent) {
		this.parent = parent;
	}

	public MNode getLc() {
		return lc;
	}

	public void setLc(MNode lc) {
		this.lc = lc;
	}

	public MNode getRc() {
		return rc;
	}

	public void setRc(MNode rc) {
		this.rc = rc;
	}
}
