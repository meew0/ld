package meew0.ld.undo;

import java.util.ArrayList;

public class UndoStack {
	ArrayList<LevelSetter> stack;
	ArrayList<UndoListener> listeners;
	int offset;

	public UndoStack() {
		stack = new ArrayList<LevelSetter>();
		listeners = new ArrayList<UndoListener>();
		offset = 0;
	}
	
	public void registerUndoListener(UndoListener l) {
		listeners.add(l);
	}
	
	public void callUndo() {
		for(UndoListener l : listeners) {
			l.onUndo();
		}
	}
	
	public void callRedo() {
		for(UndoListener l : listeners) {
			l.onRedo();
		}
	}

	public void push(int x, int y, int val) {
		LevelSetter op = new LevelSetter(x, y, val);
		push(op);
	}

	public void push(LevelSetter op) {
		op.push();
		for(int i = stack.size() - offset; i < stack.size(); i++) {
			stack.remove(i);
		}
		stack.add(op);
		offset = 0;
	}

	public void undo() {
		if (offset < stack.size()) {
			offset++;
			LevelSetter op = stack.get(stack.size() - offset);
			op.undo();
		}
		callUndo();
	}

	public void undo(int c) {
		for (int i = 0; i < c; i++)
			undo();
	}

	public void redo() {
		if (offset > 0) {
			LevelSetter op = stack.get(stack.size() - offset);
			op.push();
			offset--;
		}
		callRedo();
	}

	public void redo(int c) {
		for (int i = 0; i < c; i++)
			redo();
	}

}
