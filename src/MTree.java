import java.util.ArrayList;

public class MTree {
	public ArrayList<MNode> leaves = new ArrayList<MNode>();
	public MNode Mroot;

	public MTree(ArrayList<String> blocks_tostring) {
		for (String block : blocks_tostring) {
			MNode mn = new MNode(hashLib.toHexString(hashLib.getSHA(block)));
			leaves.add(mn);
		}

		Mroot = build_tree(leaves);

	}

	public MNode build_tree(ArrayList<MNode> leaves) {
		if (leaves.size() == 1) {
			return leaves.get(0);
		}

		ArrayList<MNode> parents = new ArrayList<MNode>();

		for (int i = 0; i < leaves.size(); i += 2) {
			MNode lc = leaves.get(i);
			MNode rc = null;
			if (i + 1 < leaves.size()) {
				rc = leaves.get(i + 1);
			} else {
				rc = leaves.get(i);
			}

			String lc_hash = lc.hash;
			String rc_hash = rc.hash;
			String both_hash = lc_hash + rc_hash;
			String parent_hash = hashLib.toHexString(hashLib.getSHA(both_hash));

			MNode parent = new MNode(parent_hash);
			lc.parent = parent;
			rc.parent = parent;
			parent.lc = lc;
			parent.rc = rc;
			parents.add(parent);
		}

		return build_tree(parents);
	}

	public ArrayList<Audit_Trail> gen_audit_trail(MNode mn, ArrayList<Audit_Trail> trail) {
		if (mn == this.Mroot) {
			trail.add(new Audit_Trail(mn.hash));
			return trail;
		}

		String pos = "";
		if (mn.parent.lc == mn) {
			pos = "r";
			trail.add(new Audit_Trail(mn.parent.rc.hash, pos));
			return gen_audit_trail(mn.parent, trail);
		} else {
			pos = "l";
			trail.add(new Audit_Trail(mn.parent.lc.hash, pos));
			return gen_audit_trail(mn.parent, trail);
		}
	}
	
	public ArrayList<Audit_Trail> get_Trail(String hash){
		ArrayList<Audit_Trail> trail = new ArrayList<Audit_Trail>();
		
		for (MNode mn : leaves) {
			if(mn.hash.equals(hash)) {
				return gen_audit_trail(mn, trail);
			}
		}
		return null;
	}
}
